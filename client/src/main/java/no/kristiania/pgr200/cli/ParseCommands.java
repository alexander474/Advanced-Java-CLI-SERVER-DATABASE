package no.kristiania.pgr200.cli;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParseCommands {

    private static List<Command> commands;

    /**
     * @return List<Command> with commands that matches the param table and mode value
     */
    public static List<Command> parse(String table, String mode) throws IllegalArgumentException {
        List<Command> commandResults = new ArrayList<>();
        for(Command c : commands){
            if(c.getTable().toUpperCase().equals(table.toUpperCase()) && c.getMode().toUpperCase().equals(mode.toUpperCase())) commandResults.add(c);
        }
        return commandResults;
    }

    /**
     *  Checks if a table is found in the commands array.
     */
    public static boolean checkForTable(String table) throws IllegalArgumentException{
        boolean found = false;
        for(Command c : commands){
            if(c.getTable().toUpperCase().equals(table.toUpperCase())) {
                found = true;
                break;
            }
        }
        if(!found) throw new IllegalArgumentException("Please enter a valid table!");
        return found;
    }

    /**
     * @return List<String> with the tables in the commands array, no duplicates.
     */
    public static List<String> getAllTables(){
        List<String> tables = new ArrayList<>();
        for(Command c : commands){
            if(!tables.contains(c.getTable().toUpperCase())){
                tables.add(c.getTable().toUpperCase());
            }
        }
        return tables;
    }

    /**
     * Prints all the tables in the array except the value "HELP" because that is not a table, but a identifier for
     * help commands.
     */
    public static String printAllTables(){
        StringBuilder sb = new StringBuilder();
        for(String s : getAllTables()){
            if(s.toUpperCase().equals("HELP")) continue;
            sb.append(s+" ");
        }
        String result = sb.toString().trim().replace(" ", ", ");
        return sb.length()>0 ? result : null;
    }

    /**
     * Checks for tables in the array that contains the input value
     */
    public static String checkForAlternativeTable(String table){
        StringBuilder sb = new StringBuilder();
        for(String s : getAllTables()){
            if(s.toUpperCase().contains(table.toUpperCase())) {
                if(!sb.toString().contains(s.toUpperCase())) {
                    sb.append(s.toUpperCase()+" ");
                }
            }
        }
        String result = sb.toString().trim().replace(" ", ", ");
        return sb.length()>0 ? result+"?" : null;
    }

    /**
     * Reads all the commands from file (JSON)
     */
    public static void parseAllCommands() throws FileNotFoundException{
        List<Command> allCommands = new ArrayList<>();
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(ParseCommands.class.getResourceAsStream("/commands.json")));
            Command[] allReadCommands = gson.fromJson(br, Command[].class);
            for(Command c : allReadCommands){
                addNewCommand(allCommands, c.getName(), c.getDescription(), c.getType(), c.getMode(), c.getTable(), c.getSubQuestionValue(), c.getSubQuestionName());
            }
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        commands = allCommands;
    }


    /**
     * Adds a new Command to the array that is passed as the first param
     */
    public static void addNewCommand(List<Command> commands, String name, String description, String type, String mode, String table, String subQuestionValue, String[] subQuestionName){
        switch (type.toUpperCase()){
            case BOOLEAN:
                commands.add(new BooleanCommand(name,description,type,mode,table,subQuestionValue,subQuestionName));
                break;
            case STRING:
                commands.add(new StringCommand(name,description,type,mode,table,subQuestionValue,subQuestionName));
                break;
            case DATE:
                commands.add(new DateCommand(name,description,type,mode,table,subQuestionValue,subQuestionName));
                break;
            case TIME:
                commands.add(new TimeCommand(name,description,type,mode,table,subQuestionValue,subQuestionName));
                break;
            case NUMBER:
                commands.add(new NumberCommand(name,description,type,mode,table,subQuestionValue,subQuestionName));
                break;
            case HELP:
                commands.add(new HelpCommand(name,description,type,mode,table,subQuestionValue,subQuestionName));
                break;
            default:
        }
    }

    private static final String BOOLEAN = "BOOLEAN";
    private static final String STRING = "STRING";
    private static final String DATE = "DATE";
    private static final String TIME = "TIME";
    private static final String NUMBER = "NUMBER";
    private static final String HELP = "HELP";





}

