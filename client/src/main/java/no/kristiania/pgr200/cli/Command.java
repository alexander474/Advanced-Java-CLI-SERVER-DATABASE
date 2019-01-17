package no.kristiania.pgr200.cli;

import java.util.Arrays;
import java.util.Objects;

public class Command<T> {

    private String name, description, type, mode, table, subQuestionValue;
    private String[] subQuestionName;
    private T value;


    public Command(String name, String description, String type, String mode, String table, String subQuestionValue, String[] subQuestionName) {
        setName(name);
        setDescription(description);
        setType(type);
        setMode(mode);
        setTable(table);
        setSubQuestionValue(subQuestionValue);
        setSubQuestionName(subQuestionName);
    }

    public Command(String name, String mode, String table) {
        setName(name);
        setMode(mode);
        setTable(table);
    }

    public Command() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public T getValue() {
        return value;
    }

    public Command setValue(T value) throws IllegalArgumentException {
        this.value = handleValue(value);
        return this;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }


    public String getSubQuestionValue() {
        return subQuestionValue;
    }

    public void setSubQuestionValue(String subQuestionValue) {
        this.subQuestionValue = subQuestionValue;
    }

    public String[] getSubQuestionName() {
        return subQuestionName;
    }

    public void setSubQuestionName(String[] subQuestionName) {
        this.subQuestionName = subQuestionName;
    }

    T handleValue(T value) throws IllegalArgumentException{
        if(value != null){
            return value;
        }
        else{
            throw new IllegalArgumentException("Invalid Input");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command<?> command = (Command<?>) o;
        return Objects.equals(name, command.name) &&
                Objects.equals(description, command.description) &&
                Objects.equals(type, command.type) &&
                Objects.equals(mode, command.mode) &&
                Objects.equals(table, command.table) &&
                Objects.equals(subQuestionValue, command.subQuestionValue) &&
                Arrays.equals(subQuestionName, command.subQuestionName) &&
                Objects.equals(value, command.value);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, description, type, mode, table, subQuestionValue, value);
        result = 31 * result + Arrays.hashCode(subQuestionName);
        return result;
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", mode='" + mode + '\'' +
                ", table='" + table + '\'' +
                ", subQuestionValue='" + subQuestionValue + '\'' +
                ", subQuestionName=" + Arrays.toString(subQuestionName) +
                ", value=" + value +
                '}';
    }
}
