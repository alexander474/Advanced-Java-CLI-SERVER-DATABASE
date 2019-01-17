package no.kristiania.pgr200.cli;


import no.kristiania.pgr200.io.RequestHandler;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class InteractiveClient {
    Scanner sc;
    int port;
    String hostName;


    public InteractiveClient(int port, String hostName){
        this.sc = new Scanner(System.in);
        this.port = port;
        this.hostName = hostName;
    }

    public InteractiveClient(Scanner sc, int port, String hostName){
        this.sc  = sc;
        this.port = port;
        this.hostName = hostName;
    }

    String start() throws IOException {
        OutputHandler.printQuestion("Please enter the mode you want to enter...");
        Object mode = ScannerHandler.scanInput(sc.nextLine());
        if(mode instanceof String) {
            return new RequestHandler(parseCommands((String) mode)).execute(port, hostName);
        } else{
          return start();
        }
    }

    /**
     * Checks the input parameter value to choose the right mode. If invalid mode is passed then it throws exception.
     * @param mode
     */
    List<Command> parseCommands(String mode) throws IOException {
            try {
                switch (mode) {
                    case "INSERT":
                        return new InteractiveInsert(sc).start(mode);
                    case "RETRIEVE":
                        return new InteractiveRetrieve(sc).start(mode);
                    case "UPDATE":
                        return new InteractiveUpdate(sc).start(mode);
                    case "DELETE":
                        return new InteractiveDelete(sc).start(mode);
                    default:
                        throw new IllegalArgumentException("You must enter a valid mode! (-h) for help");
                }
            }catch (IllegalArgumentException e) {
                OutputHandler.printErrorLine(e.getMessage());
                OutputHandler.printModesLine();
                start(); // restarts the interactive client
            }
            return null;
    }


}
