package no.kristiania.pgr200.cli;

public class NumberCommand extends Command<Number> {
    public NumberCommand(String name, String description, String type, String mode, String table,String subQuestionValue, String[] subQuestionName) {
        super(name, description, type, mode, table,subQuestionValue, subQuestionName);
    }


    @Override
    public Number getValue() {
        return super.getValue();
    }


    @Override
    public Command setValue(Number value) throws IllegalArgumentException {
        return super.setValue(value);
    }


    @Override
    Number handleValue(Number value) throws IllegalArgumentException{
        if(value != null && value instanceof Number && value.longValue()>0){
            return value;
        }
        else{
            throw new IllegalArgumentException("Invalid Input");
        }
    }
}
