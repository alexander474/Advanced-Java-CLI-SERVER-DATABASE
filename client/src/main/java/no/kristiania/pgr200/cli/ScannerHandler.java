package no.kristiania.pgr200.cli;

import java.util.Scanner;

import static java.lang.System.exit;

public class ScannerHandler {

    /**
     * Checks the input for any matching shortCut if checkForShortcut is true, if not the whole word is needed
     * if the scanner is going to replace the user input. checks the checkedForShortcut String too se if there is
     * any commando that matches, if not it will just return the original scanned user input.
     * @return
     */
    public static Object scanInput(Object o) {
        String input = (String) o;
        if(input.startsWith("-")){
            String checkedForShortCut = loopShortcuts(input);
            return checkShortCut(checkedForShortCut);
        }
        return o;
    }

    /**
     * Checks the whole word if checkForShortcut is false, if it's true the it will loop through all the posible shortcut.
     * if no match both in whole word or if it's checking shortcuts, then return the original input value.
     */
    private static Object checkShortCut(String input){
        if (input.equals("EXIT")) {
            if (exitCheck()) exit(0);
            else {
                OutputHandler.printInfo("Not exiting, returning to the interactive mode");
                return null;
            }
        } else if (input.equals(BACK)) {
            return null;
        } else if (input.equals(HELP)) {
            System.out.println(new InteractiveHelp().readHelpCommands("help", "help"));
            return null;
        } else if (input.equals(INSERT)) {
            return INSERT;
        } else if (input.equals(RETRIEVE)) {
            return RETRIEVE;
        } else if (input.equals(UPDATE)) {
            return UPDATE;
        } else if (input.equals(DELETE)) {
            return DELETE;
        } else if (input.toUpperCase().equals(TABLES)) {
            OutputHandler.printResult("TABLES FOUND", ParseCommands.printAllTables());
            return null;
        }
        return input;
    }

    /**
     * @return the value of the full word that the shortcut matches too, if no match return original user input.
     */
    private static String loopShortcuts(String mode){
        for(String s : help){
            if(s.equals(mode.toUpperCase())) return HELP;
        }
        for(String s : tables){
            if(s.equals(mode.toUpperCase())) return TABLES;
        }
        for(String s : exit){
            if(s.equals(mode.toUpperCase())) return EXIT;
        }
        for(String s : back){
            if(s.equals(mode.toUpperCase())) return BACK;
        }
        for(String s : insert){
            if(s.equals(mode.toUpperCase())) return INSERT;
        }
        for(String s : retrieve){
            if(s.equals(mode.toUpperCase())) return RETRIEVE;
        }
        for(String s : update){
            if(s.equals(mode.toUpperCase())) return UPDATE;
        }
        for(String s : delete){
            if(s.equals(mode.toUpperCase())) return DELETE;
        }
        return mode;
    }

    private static final String[] help = {"-HELP", "-H"};
    private static final String[] tables = {"-TABLE", "-T"};
    private static final String[] exit = {"-EXIT","-E"};
    private static final String[] back = {"-BACK", "-B"};
    private static final String[] insert = {"-INSERT", "-I"};
    private static final String[] retrieve = {"-RETRIEVE", "-R"};
    private static final String[] update = {"-UPDATE", "-U"};
    private static final String[] delete = {"-DELETE", "-D"};
    private static final String HELP = "HELP";
    private static final String TABLES = "TABLES";
    private static final String EXIT = "EXIT";
    private static final String BACK = "BACK";
    private static final String INSERT = "INSERT";
    private static final String RETRIEVE = "RETRIEVE";
    private static final String UPDATE = "UPDATE";
    private static final String DELETE = "DELETE";


    /**
     * @return true if user write (Y) to exit or false if user write anything else
     */
    private static boolean exitCheck(){
        Scanner sc = new Scanner(System.in);
        OutputHandler.printCriticalQuestion("Are you sure you want to exit?(Y/N)");
        String exitCheck = sc.nextLine().toUpperCase();
        return exitCheck.contains("Y")||exitCheck.contains("T");
    }

}
