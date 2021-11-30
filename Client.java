import java.io.*;
import java.net.Socket;
import java.util.*;
/**
 * This class represents the clients interaction with the server.
 */
public class Client {
    /**
     * This method creates a random binary matrix by the user (input rows and columns).
     * @return Matrix (a random matrix).
     */
    public static Matrix matrixRequest(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter row for random Matrix");
        int row = scanner.nextInt();
        while(!(row>0)){
            System.out.println("Please enter a valid row for matrix");
            row = scanner.nextInt();
        }
        System.out.println("Please enter column for random Matrix");
        int column = scanner.nextInt();
        while(!(column>0)){
            System.out.println("Please enter a valid column for matrix");
            column = scanner.nextInt();
        }
        return new Matrix(row, column);
    }
    /**
     * This method creates a random binary matrix by the user (input rows and columns), in the ranges of 0 - 50.
     * @return Matrix (a random matrix).
     */
    public static Matrix matrixRequestWithLimitation(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter row for random Matrix in range 0-50");
        int row = scanner.nextInt();
        while(!(row>0 && row<= 50)){
            System.out.println("Please enter a valid row for matrix in range 0-50");
            row = scanner.nextInt();
        }
        System.out.println("Please enter column for random Matrix in range 0-50");
        int column = scanner.nextInt();
        while(!(column>0 && column<=50)){
            System.out.println("Please enter a valid column for matrix in range 0-50");
            column = scanner.nextInt();
        }
        return new Matrix(row, column);
    }
    /**
     * This method creates a random weighted matrix by the user (input rows and columns).
     * @return Matrix (a random matrix).
     */
    public static Matrix matrixWeightRequest(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter row for random Matrix");
        int row = scanner.nextInt();
        while(!(row>0)){
            System.out.println("Please enter a valid row for matrix");
            row = scanner.nextInt();
        }
        System.out.println("Please enter column for random Matrix");
        int column = scanner.nextInt();
        while(!(column>0)){
            System.out.println("Please enter a valid column for matrix");
            column = scanner.nextInt();
        }
        return new Matrix(row, column,1000);
    }
    /**
     * This method asks the user which index in the matrix he would like to choose.
     * @param matrix from the requested matrix.
     * @return Index
     */
    public static Index indexRequest(Matrix matrix){
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        while(!(row>=0 && row< matrix.primitiveMatrix.length)){
            System.out.println("Please enter existing row at matrix");
            row = scanner.nextInt();
        }
        System.out.println("Please enter column for Index");
        int column = scanner.nextInt();
        while(!(column>=0 && column< matrix.primitiveMatrix[0].length)){
            System.out.println("Please enter column at matrix");
            column = scanner.nextInt();
        }
        return new Index(row , column);
    }

    /**
     * This method prints the main menu
     */
    public static void printMenu(){
        System.out.println("Please choose the following:");
        System.out.println("1--> Find the SCC list (first task)");
        System.out.println("2--> Find the shortest paths from one index to another (second task)");
        System.out.println("3--> Find the number of submarines (third task)");
        System.out.println("4--> Find the lightest paths from one index to another (fourth task)");
        System.out.println("0--> Exit the program");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket =new Socket("127.0.0.1",8010);
        System.out.println("client: Created Socket");

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream toServer=new ObjectOutputStream(outputStream);
        ObjectInputStream fromServer=new ObjectInputStream(inputStream);
        boolean doWork = true;
        Scanner scanner = new Scanner(System.in);
        // Two pre built matrices in case the matrix is not a random one.
//        int[][] source = {
//                {1, 1, 0, 1, 1},
//                {1, 1, 0, 0, 0},
//                {1, 1, 0, 0, 1},
//                {1, 1, 0, 1, 0},
//                {1, 1, 0, 1, 0}
//        };
//        int[][] source2 = {
//                {100,100, 100},
//                {500, 900, 300},
//        };


        while (doWork){
            printMenu();
            switch (scanner.nextInt()){
                case 1:
                    // Works for large inputs as well
                    Matrix matrix = matrixRequest();
                    //send "firstTask" command then write 2d array to socket
                    toServer.writeObject("firstTask");
                    toServer.writeObject(matrix);
                    List<HashSet<Index>> listOfSCC = (List<HashSet<Index>>) fromServer.readObject();
                    System.out.println("strong connected components of graph: \n"+ listOfSCC + "\n");
                    break;
                case 2:
                    // Works for large inputs as well

                    matrix= matrixRequestWithLimitation();
                    System.out.println("Please enter a row for source index");
                    Index sourceIndex = indexRequest(matrix);
                    System.out.println("Please enter a row for destination index");
                    Index destinationIndex = indexRequest(matrix);
                    toServer.writeObject("secondTask");
                    toServer.writeObject(matrix);
                    toServer.writeObject(sourceIndex);
                    toServer.writeObject(destinationIndex);
                    List<ArrayList<Node>> shortestPaths = ((List<ArrayList<Node>>) fromServer.readObject());
                    System.out.println("The shortest path are: \n" + shortestPaths + "\n");
                    break;
                case 3:
                    // Works for large inputs as well

                    matrix = matrixRequest();
                    toServer.writeObject("thirdTask");
                    toServer.writeObject(matrix);
                    int numberOfSubmarine = (int) fromServer.readObject();
                    System.out.println("Number of submarine is: " + numberOfSubmarine+ "\n");
                    break;
                case 4:
                    // Works for large inputs as well

                    matrix = matrixWeightRequest();
                    System.out.println("Please enter a row for source index");
                    sourceIndex = indexRequest(matrix);
                    System.out.println("Please enter a row for destination index");
                    destinationIndex = indexRequest(matrix);
                    toServer.writeObject("fourthTask");
                    //toServer.writeObject(source2);
                    toServer.writeObject(matrix);
                    toServer.writeObject(sourceIndex);
                    toServer.writeObject(destinationIndex);
                    HashSet<ArrayList<Node<Index>>> simplePaths = (HashSet<ArrayList<Node<Index>>>) fromServer.readObject();
                    System.out.println("from client - all simple paths Indices are:  \n"+ simplePaths+ "\n");
                    break;
                case 0:
                    doWork=false;
                    toServer.writeObject("stop");
                    System.out.println("client: Close all streams");
                    fromServer.close();
                    toServer.close();
                    socket.close();
                    System.out.println("client: Closed operational socket");
                    break;

                default:
                    System.out.println("Please enter the right number!");
                    break;


            }
        }

    }
}