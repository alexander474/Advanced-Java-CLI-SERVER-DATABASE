package no.kristiania.pgr200.cli;

import java.util.List;

public class BasicCommand<T> extends Command<T> {

    public BasicCommand(String name, String mode, String table) {
        super(name, mode, table);
    }


    @Override
    public T getValue() {
        return super.getValue();
    }

    @Override
    public Command setValue(T value) {
        return super.setValue(value);
    }

    @Override
    T handleValue(T value) {
        return value;
    }
}
