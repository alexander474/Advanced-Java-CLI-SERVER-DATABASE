package no.kristiania.pgr200.cli;

public class BooleanCommand extends Command<String> {

    public BooleanCommand(String name, String description, String type, String mode, String table,String subQuestionValue, String[] subQuestionName) {
        super(name, description, type, mode, table,subQuestionValue, subQuestionName);
    }


    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public Command setValue(String value) {
        String result = handleValue(value);
        if(result.toUpperCase().contains("Y")||result.toUpperCase().contains("T")){
            result = "TRUE";
        }else if(result.toUpperCase().contains("N")||result.toUpperCase().contains("F")){
            result = "FALSE";
        }
        return super.setValue(result);
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
