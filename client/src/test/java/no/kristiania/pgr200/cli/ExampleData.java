package no.kristiania.pgr200.cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.kristiania.pgr200.db.Conference;

import java.util.ArrayList;
import java.util.List;

public class ExampleData {

    public static List<Command> getStringINSERTCommand(){
        List<Command> commands = new ArrayList<>();
        commands.add(exampleCommand("name", "description", "type", "insert", "track", "none", new String[]{"none"}, "value"));
        return commands;
    }

    public static Command getSingleStringINSERTCommand(){
        return exampleCommand("name", "description", "type", "insert", "track", "none", new String[]{"none"}, "value");
    }

    public static List<Command> getStringGETCommand(){
        List<Command> commands = new ArrayList<>();
        commands.add(exampleCommand("name", "description", "type", "retrieve", "track", "none", new String[]{"none"}, "value"));
        commands.add(exampleCommand("id", "description", "type", "retrieve", "track", "none", new String[]{"none"}, 1));
        return commands;
    }

    public static Command getSingleStringGETCommand(){
        return exampleCommand("id", "description", "type", "retrieve", "track", "none", new String[]{"none"}, 1);
    }


    public static Conference getConferenceExample(){
        return exampleConference(1, "title", "description", "10-10-2010", "10-10-2018");
    }


    private static <T> Command<T> exampleCommand(String name, String description, String type, String mode, String table, String subQuestionValue, String[] subQuestionName, T value){
        Command<T> c = new Command<>();
        c.setName(name);
        c.setDescription(description);
        c.setType(type);
        c.setMode(mode);
        c.setTable(table);
        c.setSubQuestionValue(subQuestionValue);
        c.setSubQuestionName(subQuestionName);
        c.setValue(value);
        return c;
    }

    private static Conference exampleConference(int id, String title, String description, String date_start, String date_end){
        Conference c = new Conference();
        c.setId(id);
        c.setTitle(title);
        c.setDescription(description);
        c.setDate_start(date_start);
        c.setDate_end(date_end);
        return c;
    }

    public static String getJson(Object value) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(value);
    }
}
