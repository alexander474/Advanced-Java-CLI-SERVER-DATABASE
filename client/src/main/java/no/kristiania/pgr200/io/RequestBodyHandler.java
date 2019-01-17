package no.kristiania.pgr200.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import no.kristiania.pgr200.cli.Command;
import no.kristiania.pgr200.common.DateHandler;

import java.util.*;

public class RequestBodyHandler<K,T extends Command> {
    List<T> body;
    HashMap<String, K> fields;
    String table, mode;
    boolean isID;


    public RequestBodyHandler(List<T> body, String table, String mode) {
        this.body = body;
        this.table = table;
        this.mode = mode;
        addAllFields();
        isID = false;
    }

    public String getRequestBody(){
        return getJson(fields);
    }


    private void addAllFields(){
        fields = new HashMap<>();
        for(T command : body){
            checkForElement(command);
            if(command.getTable().toUpperCase().equals(this.table.toUpperCase())) addNewField(command.getName(), (K) command.getValue(), command.getType());
        }
    }

    private void checkForElement(T command){
            if(command.getName().toUpperCase().equals("ID") && command.getValue() != null){
                this.isID = true;
            }else{
                this.isID = false;
            }
    }

    private boolean isRetrieve(){
        return this.mode.toUpperCase().equals("RETRIEVE");
    }


    private void addNewField(String name, K value, String type){
        if(name == null || value == null) return;
        if(isRetrieve() && !isID) return; // Do not want to append to field array when there is no id, want all entries by default
        fields.put(name, handleDateTime(type,value));
    }


    private String getJson(HashMap<String,K> list) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(list);
    }

    private K handleDateTime(String type, K value){
        if(type.toUpperCase().equals("TIME")) return (K) DateHandler.parseTime((String)value);
        return value;
    }
}
