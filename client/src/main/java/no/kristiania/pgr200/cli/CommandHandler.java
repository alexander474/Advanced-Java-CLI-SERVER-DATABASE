package no.kristiania.pgr200.cli;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class CommandHandler{
    private List<Command> commands;
    private List<String> commandsDontAsk;
    Scanner sc;
    private String mode;

    public CommandHandler() {
        this(new Scanner(System.in));
        commands = new ArrayList<>();
        commandsDontAsk = new ArrayList<>();
    }

    public CommandHandler(Scanner sc){
        commands = new ArrayList<>();
        commandsDontAsk = new ArrayList<>();
        this.sc = sc;
    }

    /**
     * Starts the normal mode where you choose a table and an array of commands will be looped.
     * if the command isn't named "table" and the name dosen't match any of the values in the commandDontAsk then
     * print the description of the command and handle the input from the user and then check if the command
     * has any rules for subquestions that is depending on the current command(commandQuestion).
     * @param mode
     */
    List<Command> start(String mode){
        this.mode = mode;
        ParseCommands.addNewCommand(commands,"table", "Please choose the table you want to operate on", "String", mode, "info", "none", new String[]{"none"});
        Command table = chooseTable();
        if(table != null) {
            for (Command c : commands) {
                if (!c.getName().toUpperCase().equals(table.getName().toUpperCase()) && !commandsDontAsk.contains(c.getName().toUpperCase())) {
                    OutputHandler.printQuestion(c.getDescription());
                    handleInput(c);
                    checkForSubQuestion(c);
                }
            }
        }
        return commands;
    }


    /**
     * Asks the user for the table it wants to "use" and adds the user input to the table command value.
     * if the value isn't null and the table exists in the csv file it will check if it's not the value "help".
     * if it passes it will insert all the commands for the choosen table to the commands array.
     *
     * If the table that the user typed in dosen't exist in the csv file will it throw and error and check for
     * probable tables the input can be and run the function again for a new table choosing.
     */
    Command chooseTable(){
        Command table = null;
        try {
            table = getCommand("table");
            OutputHandler.printQuestion(table.getDescription());
            handleInput(table);
            String tableValue = table != null ? table.getValue().toString() : null;
            if (tableValue != null && ParseCommands.checkForTable(tableValue)){
                if(!tableValue.equals("help")) {
                    OutputHandler.printInfo("Getting commands for "+ tableValue);
                    insertCommands(table.getValue().toString(), mode);
                }
            }
        } catch (IllegalArgumentException e){
            String commands = ParseCommands.checkForAlternativeTable(getCommand("table").getValue().toString());
            OutputHandler.printErrorLine(e.getMessage());
            if(commands != null) OutputHandler.printResult("Did you mean",commands+" ");
            chooseTable();
        }
        return table;
    }


    /**
     * Checks for the scanner input and adds the value to the right command instance.
     *
     * Catches DateTimeException if the input dosen't follow the date rule of (dd-mm-yyyy), if exception is thrown
     * It wil print error and ask for a new input on the same command.
     * @param c
     */
    private void handleInput(Command c) {
        try {
            Object input = ScannerHandler.scanInput(sc.nextLine());
            if(input != null) {
                if (c instanceof StringCommand)
                    c.setValue(input);
                else if (c instanceof DateCommand)
                    c.setValue(input);
                else if (c instanceof TimeCommand)
                    c.setValue(input);
                else if (c instanceof NumberCommand)
                    c.setValue(Long.parseLong(input.toString()));
                else if (c instanceof BooleanCommand)
                    c.setValue(input);
                else if (c instanceof HelpCommand)
                    c.setValue(input);
            }else{
                OutputHandler.printInfo("Sometimes some modes cannot execute because you are inside a critical step");
                OutputHandler.printCriticalQuestion("Please enter valid information");
                handleInput(c);
            }
        }catch (DateTimeParseException e){
            OutputHandler.printCriticalQuestion("Please enter a valid input: date (dd-mm-yyyy), time (hh:mm)");
            handleInput(c);
        } catch (IllegalArgumentException e){
            OutputHandler.printCriticalQuestion(e.getMessage());
            handleInput(c);
        }
    }

    void insertCommands(String table, String mode){
        List<Command> inputListCommands = readAllCommandsByTable(table, mode);
        for(Command c : inputListCommands){
            addCommand(c);
        }
    }


    void addCommand(Command c){
        commands.add(c);
    }


    List<Command> getAllCommands(){
        return commands;
    }


    public Command getCommand(String name){
        for(Command c : commands){
            if(c.getName().toUpperCase().equals(name.toUpperCase())) return c;
        }
        return null;
    }


    /**
     * Checks the subquestion value of the command and splites the line on _
     * then gets the values after the colon ":"
     * if the name and value dosen't equal NONE then we split by space on the names, and if
     * the value dosen match the value from the command then add the name to the array so
     * we dont ask for imput on that command
     *
     * names:id_values:FALSE
     * [names:id,values:FALSE]
     * name = id & value = FALSE
     * @param c
     */
    private void checkForSubQuestion(Command c){
        String value = c.getSubQuestionValue();
        String[] names = c.getSubQuestionName();
        if(names != null && value != null){
            if (!value.toUpperCase().equals("NONE")) {
                if (!value.toUpperCase().equals(c.getValue().toString().toUpperCase())) {
                    for (String s : names) {
                        commandsDontAsk.add(s.toUpperCase());
                    }
                }
            }
        }
    }

    public String readHelpCommands(String table, String mode) {
        List<Command> commands = ParseCommands.parse(table, mode);
        StringBuilder sb = new StringBuilder();
        sb.append(OutputHandler.printCommandHelpHeader());
        for(Command c : commands){
            sb.append(OutputHandler.printCommandHelpLineWithSpaces(c.getName(),c.getDescription(), c.getType()));
        }
        return sb.toString();
    }

    public List<Command> readAllCommandsByTable(String table, String mode){
        return ParseCommands.parse(table.toUpperCase(), mode.toUpperCase());
    }



    /**
     * @param name
     * @return The command value of the command that has a name matching the parameter input, if no match return null.
     */
    public Object getCommandValue(String name){
        for(Command c : commands){
            if(c.getName().toUpperCase().equals(name.toUpperCase())) return c.getValue();
        }
        return null;
    }
}
