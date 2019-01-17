package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.common.PropertyReader;
import no.kristiania.pgr200.server.*;
import no.kristiania.pgr200.server.requesthandlers.*;
import org.fusesource.jansi.AnsiConsole;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Arrays;

public class ClientMain {
    private static int PORT;
    private static String HOST_NAME;

  public static void main(String[] args) throws IOException {
      AnsiConsole.systemInstall();
    try{
        ParseCommands.parseAllCommands();
        String[] checkedArgument = readProperties(args);

        if(args.length > 0){
          String result = new DecodeArgs().decode(checkedArgument, PORT, HOST_NAME);
          OutputHandler.printResult("RESULT", result); // The Database Response Printed out to the user
        } else {
          OutputHandler.printErrorLine("You must enter use the parameters START or LIST [TABLENAME] PLUS IF YOU WANT ONLY ONE: [ID]!");
          OutputHandler.printInfo("To update properties enter: ");
          OutputHandler.printInfo("UPDATE CLIENT [HOST_NAME] [PORT]");
          OutputHandler.printInfo("UPDATE DATABASE [URL] [USERNAME] [PASSWORD]");
        }
    }catch (FileNotFoundException e){
      OutputHandler.printErrorLine("Error: "+e.getMessage()+"\n\n");
      e.printStackTrace();
    }

  }
  //Oppdater innlevering.properties med dataSource.url, dataSource.username, dataSource.password

  private static String[] readProperties(String[] args) throws IOException {
      PropertyReader propertyReader = new PropertyReader("/innlevering.properties");
      if(args.length>=4 && args[0].toUpperCase().equals("UPDATE")){
          String prefix = args[1].toUpperCase();
          if(prefix.equals("CLIENT")){
              propertyReader.setProperty("HOSTNAME", args[2]);
              propertyReader.setProperty("PORT", args[3]);
          }else if(prefix.equals("DATABASE")){
              propertyReader.setProperty("URL", args[2]);
              propertyReader.setProperty("USER", args[3]);
              propertyReader.setProperty("PASSWORD", args[4]);
          }
          OutputHandler.printInfo("CHANGED PROPERTIES FILE");
          PORT = Integer.parseInt(propertyReader.getProperty("PORT"));
          HOST_NAME = propertyReader.getProperty("HOST_NAME");
          OutputHandler.printInfo("LOOKING AT PORT: "+PORT+"\nLOOKING AT HOST: "+HOST_NAME);
          return new String[]{"START"};
      }
      PORT = Integer.parseInt(propertyReader.getProperty("PORT"));
      HOST_NAME = propertyReader.getProperty("HOST_NAME");
      OutputHandler.printInfo("LOOKING AT PORT: "+PORT+"\nLOOKING AT HOST: "+HOST_NAME);
      return args;
  }
}
