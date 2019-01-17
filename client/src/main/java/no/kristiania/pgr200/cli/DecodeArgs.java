package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.io.RequestHandler;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class DecodeArgs {
    Scanner sc;
    StringBuilder sb;
    String argMode, hostName;
    int port;

    public DecodeArgs() {
        sc = new Scanner(System.in);
        sb = new StringBuilder();
    }

    public DecodeArgs(Scanner sc) {
        this.sc = sc;
        sb = new StringBuilder();
    }

    public String decode(String[] args, int port, String hostName) throws IOException {
        this.argMode = args[0].toUpperCase();
        this.port = port;
        this.hostName = hostName;
        switch(this.argMode){
            case "START":
                OutputHandler.printWelcome();
                return handleResult(new InteractiveClient(this.sc, this.port, this.hostName).start());
            case "LIST":
                if(args.length>1) return handleResult(handleListCommand(args));
                else{
                    sb.append("When using the parameter 'LIST' then you will need to specify table and optionally an ID\n");
                    sb.append("FORMAT: LIST [TABLE] [ID]");
                    sb.append("Ex: LIST CONFERENCE 1");
                }
                break;
            case "RICKROLL":
                if(Desktop.isDesktopSupported()){
                    try {
                        Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                //do stuff
        }
        return sb.toString();
    }

    String handleResult(String result) throws IOException {
        OutputHandler.printResult("RESULT", result); // The Database Response Printed out to the user
        if(this.argMode.equals("START")){
            if(!exitCheck()){
                OutputHandler.printInfo("NOT EXITING");
                decode(new String[]{"START"}, this.port, this.hostName);
            }
        }
        return "EXIT COMPLETE";
    }

    private boolean exitCheck() {
        OutputHandler.printCriticalQuestion("Do you want to make another request?(Y/N)");
        String result = sc.nextLine();
        return result.toUpperCase().contains("N");
    }

    private String handleListCommand(String[] args) throws IOException {
        Number id = null;
        if(args.length>2){
            id = Integer.parseInt(args[2]);
        }
        switch (args[1].toUpperCase()) {
            case "CONFERENCE":
                return new RequestHandler("conference", "RETRIEVE", id).execute(this.port, this.hostName);
            case "TRACK":
                return new RequestHandler("track", "RETRIEVE", id).execute(this.port, this.hostName);
            case "TALK":
                return new RequestHandler("talk", "RETRIEVE", id).execute(this.port, this.hostName);
        }
        return null;
    }

}
