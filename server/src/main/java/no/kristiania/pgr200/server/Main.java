package no.kristiania.pgr200.server;

import no.kristiania.pgr200.db.LocalDataSource;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerBadHttpMethod;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerCapi;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerEcho;
import no.kristiania.pgr200.server.requesthandlers.HttpServerRequestHandlerURL;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        if(args.length > 0 && args[0].toLowerCase().equals("reset")){
            DataSource dataSource = new LocalDataSource().resetDataSource();
            System.out.println("Database has been reset.");
            System.exit(0);
        }
        new HttpServerConfig();
        int port = 9010;
        DataSource dataSource = new LocalDataSource().createDataSource();
        System.out.println("Server listening on port " + port);
        HttpServerListener listener = new HttpServerListener(
            Arrays.asList(new HttpServerRequestHandlerCapi(dataSource),
                    new HttpServerRequestHandlerBadHttpMethod(),
                    new HttpServerRequestHandlerEcho(),
                    new HttpServerRequestHandlerURL()),
                new HttpServerParserRequest(),
                new HttpServerWriterResponse()
        );

        try {
            listener.start(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
