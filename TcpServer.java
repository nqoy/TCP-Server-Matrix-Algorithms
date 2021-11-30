import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class represent the TCP server Handle a request from the clients.
 */
public class TcpServer {

    private final int port;
    private volatile boolean stopServer;
    private ThreadPoolExecutor threadPool;
    private IHandler requestHandler;

    private final ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    /**
     * Constructor - initialize data members (although they are initialized by default)
     * @param port
     */
    public TcpServer(int port) {
        this.port = port;
        stopServer = false;
        threadPool = null;
        requestHandler = null;
    }

    /**
     *    if no port is specified - one will be automatically allocated by OS
     *                  backlog parameter- number of maximum pending requests
     *                  ServerSocket constructor - socket creation + bind to a specific port
     *                  Server Socket API:
     *                  1. create socket
     *                  2. bind to a specific port number
     *                  3. listen for incoming connections (a client initiates a tcp connection with server)
     *                  4. try to accept (if 3-way handshake is successful)
     *                  5. return operational socket (2 way pipeline)
     *
     *                   server will handle each client in a separate thread
     *                  define every client as a Runnable task to execute
     * @param concreteHandler
     */
    public void run(IHandler concreteHandler) {
        this.requestHandler = concreteHandler;

        new Thread(() -> {
            // lazy loading
            threadPool = new ThreadPoolExecutor(3, 5, 10,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>());
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (!stopServer) {
                    Socket serverToSpecificClient = serverSocket.accept();
                    Runnable clientHandling = () -> {
                        try {
                            requestHandler.handle(serverToSpecificClient.getInputStream(),
                                    serverToSpecificClient.getOutputStream());
                            // finished handling client. now close all streams
                            //serverToSpecificClient.getInputStream().close();
                           //serverToSpecificClient.getOutputStream().close();
                            serverToSpecificClient.close();
                        } catch (IOException | ClassNotFoundException ioException) {
                            System.err.println(ioException.getMessage());
                        }
                    };
                    threadPool.execute(clientHandling);
                }
                serverSocket.close();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }).start();
    }

    /**
     * Stop method- responsible to stop the server.
     * Implementation using double-check locking.
     */
    public void stop(){
        if(!stopServer){
            try{
                readWriteLock.writeLock().lock();
                if(!stopServer){
                    if(threadPool!=null)
                        threadPool.shutdown();
                }
            }catch (SecurityException se){
                se.printStackTrace();
            }finally {
                stopServer = true;
                readWriteLock.writeLock().unlock();
                System.out.println("Server shut down successfully");
            }
        }
    }

    public void jvmInfo(){
        System.out.println(ProcessHandle.current().pid());
        System.out.println(Runtime.getRuntime().maxMemory());
    }


    public static void main(String[] args) {
        TcpServer myServer = new TcpServer(8010);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter any key to start the server, enter 'stop' to stop the server");
        String message = scanner.nextLine();
        while(!message.equals("stop")){
            myServer.run(new MatrixIHandler());
            message = scanner.nextLine();
        }
        myServer.stop();


    }
}
