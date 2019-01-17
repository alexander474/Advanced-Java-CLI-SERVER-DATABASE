package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.common.DateHandler;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateCommand extends Command<String> {
    public DateCommand(String name, String description, String type, String mode, String table, String subQuestionValue, String[] subQuestionName) {
        super(name, description, type, mode, table, subQuestionValue, subQuestionName);
    }

    @Override
    public String getValue() {
        return super.getValue();
    }


    @Override
    public Command setValue(String value) throws DateTimeParseException {
        return super.setValue(handleDate(value));
    }

    String handleDate(String date) throws DateTimeParseException {
        LocalDate.parse(date, DateHandler.dateTimeFormatter);
        return date;
    }

    @Override
    String handleValue(String value) throws IllegalArgumentException{
        if(value != null && value instanceof String && !value.isEmpty()){
            return value;
        }
        else{
            throw new IllegalArgumentException("Invalid Input");
        }
    }
}
