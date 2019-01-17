package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.server.*;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerBadHttpMethod;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerEcho;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerURL;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InteractiveClientTest {

    private static HttpServerListener server;
    private int port;
    private String host;

    @BeforeClass
    public static void startServer() throws IOException {
        ParseCommands.parseAllCommands();
        server = new HttpServerListener(
                Arrays.asList(new HttpServerRequestHandlerBadHttpMethod(),
                        new HttpServerRequestHandlerEcho(),
                        new HttpServerRequestHandlerEcho(),
                        new HttpServerRequestHandlerURL()),
                new HttpServerParserRequest(),
                new HttpServerWriterResponse()
        );
        server.start(0);
    }

    @Before
    public void insertAllCommands(){
        port = server.getPort();
        host = "localhost";
    }

    @Test
    public void testInsertCommands() throws IOException {
        String result = exampleCommand("-i\r\nconference\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat(result).isNotNull();
    }


    @Test
    public void testRetrieveCommands() throws IOException {
        String result = exampleCommand("-r\r\nconference\r\ny\r\ny\r\n");
        assertThat(result).isNotNull();
    }

    @Test
    public void testUpdateCommands() throws IOException {
        String result = exampleCommand("-u\r\nconference\r\n1\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat(result).isNotNull();
    }

    @Test
    public void testDeleteCommands() throws IOException {
        String result = exampleCommand("-d\r\nconference\r\n1\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        assertThat(result).isNotNull();
    }



    private Scanner writeToScanner(String message){
        InputStream in = new ByteArrayInputStream(message.getBytes());
        return new Scanner(in);
    }


    private String exampleCommand(String message) throws IOException {
        Scanner sc = writeToScanner(message);
        return new InteractiveClient(sc, port, host).start();
    }


}
