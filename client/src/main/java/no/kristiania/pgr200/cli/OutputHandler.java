package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.common.ConsoleColors;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class OutputHandler {

    public static void printQuestion(String message){
        if(message == null) return;
        String formattedText = "\n"+ConsoleColors.YELLOW+message+"\n"+ConsoleColors.BLUE+">>"+ConsoleColors.RESET;
        System.out.println(formattedText);
        log(formattedText);
    }
    public static void printCriticalQuestion(String message){
        if(message == null) return;
        String formattedText = ConsoleColors.RED+message+"\n"+ConsoleColors.BLUE+">>"+ConsoleColors.RESET;
        System.out.println(formattedText);
        log(formattedText);
    }
    public static void printInfo(String message){
        if(message == null) return;
        String formattedText = ConsoleColors.WHITE+message+"\n"+ConsoleColors.RESET;
        System.out.println(formattedText);
        log(formattedText);
    }
    public static void printResult(String message, String value){
        if(message == null && value == null) return;
        String formatedMessage = ConsoleColors.WHITE+message.trim();
        if(!message.trim().endsWith(":")) formatedMessage+=": ";
        String formattedText = formatedMessage+ConsoleColors.GREEN+"\n"+value+"\n"+ConsoleColors.RESET;
        System.out.println(formattedText);
        log(formattedText);
    }
    public static void printLine(String message){
        if(message == null) return;
        String formattedText = message;
        System.out.println(formattedText);
        log(formattedText);
    }
    public static void printYellowLine(String message){
        if(message == null) return;
        String formattedText = ConsoleColors.YELLOW+message+ConsoleColors.RESET;
        System.out.println(formattedText);
        log(formattedText);
    }
    public static void printGreenLine(String message){
        if(message == null) return;
        String formattedText = ConsoleColors.GREEN+message+ConsoleColors.RESET;
        System.out.println(formattedText);
        log(formattedText);
    }
    public static void printRedLine(String message){
        if(message == null) return;
        String formattedText = ConsoleColors.RED+message+ConsoleColors.RESET;
        System.out.println(formattedText);
        log(formattedText);
    }
    public static void printBlueLine(String message){
        if(message == null) return;
        String formattedText = ConsoleColors.BLUE+message+ConsoleColors.RESET;
        System.out.println(formattedText);
        log(formattedText);
    }
    public static void printErrorLine(String message){
        if(message == null) return;
        String formattedText = message;
        System.err.println(formattedText);
        log(formattedText);
    }


    public static void printWelcome(){
        printBlueLine("WELCOME TO THE INTERACTIVE MODE!");
        printModesLine();
    }

    public static void printModesLine(){
        printResult("Modes","(-e) exit, (-b) back, (-h) help, (-t) display all tables, (-i) insert, (-r) retrieve, (-u) update, (-d) delete");
    }

    public static String printCommandHelpHeader(){
        StringBuilder sb = new StringBuilder();
        String head = String.format("%-60s%-70s%-60s\n", "| NAME", "DESCRIPTION", "TYPE |");
        sb.append(head);
        sb.append("|======================================================================================================================================|\n");
        return sb.toString();
    }

    public static String printCommandHelpLineWithSpaces(String valueOne, String valueTwo, String valueThree){
        return String.format("%-30s%-100s%-30s\n", "| "+valueOne, valueTwo, valueThree+" |");
    }


    public static void log(String log){}
    /**
     public static final Logger logger = Logger.getLogger(OutputHandler.class.getName());

     public static void log(String log){
     // Construct a default FileHandler.
     // "%t" denotes the system temp directory, kept in environment variable "tmp"
     Handler fh = null;  // append is true
     try {
     fh = new FileHandler("log.log", true);

     //    fh.setFormatter(new SimpleFormatter());  // Set the log format
     // Add the FileHandler to the logger.
     logger.addHandler(fh);
     // Set the logger level to produce logs at this level and above.
     logger.setLevel(Level.FINE);

     logger.fine(log);
     // below the logger's level
     } catch (IOException e) {
     e.printStackTrace();
     }

     fh.flush();
     fh.close();
     }**/

}
