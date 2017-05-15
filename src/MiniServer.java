import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;

import fi.iki.elonen.util.ServerRunner;


/**
 * An example of subclassing NanoHTTPD to make a custom HTTP server.
 */
public class MiniServer extends NanoHTTPD {

    /**
     * logger to log to.
     */
    private static final Logger LOG = Logger.getLogger(MiniServer.class.getName());

    public MiniServer() throws IOException {
        super(8080);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }
    @Override
    public Response serve(IHTTPSession session) {
        FileInputStream fis = null;
        File file = new File("C:\\screen\\screenshot2.png"); //path exists and its correct
        long size = file.length();

        try {

            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return newFixedLengthResponse(Response.Status.OK, "image/jpeg", fis, size);

    }
}