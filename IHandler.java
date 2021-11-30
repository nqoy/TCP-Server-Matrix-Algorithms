import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface handles the input and output of the server.
 */
public interface IHandler {
    /**
     * Responsible to handle with input and output
     * @param fromClient
     * @param toClient
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public abstract void handle(InputStream fromClient,
                                OutputStream toClient) throws IOException, ClassNotFoundException;
}
