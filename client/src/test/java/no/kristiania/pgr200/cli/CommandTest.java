package no.kristiania.pgr200.cli;

import org.junit.Test;


import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CommandTest {

    @Test
    public void testCommandWithStringValue(){
        Command c = exampleCommand("value");
        assertThat(c.getName()).isEqualTo("name");
        assertThat(c.getDescription()).isEqualTo("description");
        assertThat(c.getType()).isEqualTo("String");
        assertThat(c.getMode()).isEqualTo("mode");
        assertThat(c.getTable()).isEqualTo("table");
        assertThat(c.getSubQuestionValue()).isEqualTo("none");
        assertThat(c.getSubQuestionName()).isEqualTo(new String[]{"none"});
        assertThat(c.getValue()).isEqualTo("value");
    }

    @Test
    public void testCommandWithNumberValue(){
        Command c = exampleCommand(10);
        assertThat(c.getName()).isEqualTo("name");
        assertThat(c.getDescription()).isEqualTo("description");
        assertThat(c.getType()).isEqualTo("String");
        assertThat(c.getMode()).isEqualTo("mode");
        assertThat(c.getTable()).isEqualTo("table");
        assertThat(c.getSubQuestionValue()).isEqualTo("none");
        assertThat(c.getSubQuestionName()).isEqualTo(new String[]{"none"});
        assertThat(c.getValue()).isEqualTo(10);
    }

    @Test
    public void testBasicCommand(){
        BasicCommand c = exampleBasicCommand();
        c.setValue("value");
        assertThat(c.getName()).isEqualTo("name");
        assertThat(c.getMode()).isEqualTo("mode");
        assertThat(c.getTable()).isEqualTo("table");
        assertThat(c.getValue()).isEqualTo("value");
        assertThat(c).isInstanceOf(BasicCommand.class);
    }

    @Test
    public void testBasicCommandThrowsIllegalArgumentException(){
        BasicCommand c = exampleBasicCommand();
        assertThat(c.getValue()).isNull();
        assertThat(c.getName()).isEqualTo("name");
        assertThat(c.getMode()).isEqualTo("mode");
        assertThat(c.getTable()).isEqualTo("table");
        assertThat(c).isInstanceOf(BasicCommand.class);
    }

    @Test
    public void testStringCommand(){
        StringCommand command = exampleStringCommand();
        command.setValue("LOREM IPSUM");

        assertThat(command.getValue()).isEqualTo("LOREM IPSUM");
    }

    @Test
    public void testStringCommandThrowsIllegalArgumentException(){
        StringCommand command = exampleStringCommand();
        assertThatThrownBy(() -> {
            command.setValue(null);
            command.setValue("");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid Input");
    }

    @Test
    public void testHelpCommand(){
        HelpCommand command = exampleHelpCommand();
        command.setValue("LOREM IPSUM");

        assertThat(command.getValue()).isEqualTo("LOREM IPSUM");
    }

    @Test
    public void testHelpCommandThrowsIllegalArgumentException(){
        HelpCommand command = exampleHelpCommand();
        assertThatThrownBy(() -> {
            command.setValue(null);
            command.setValue("");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid Input");
    }

    @Test
    public void testDateCommand(){
        DateCommand command = exampleDateCommand();
        command.setValue("10-10-2010");

        assertThat(command.getValue()).isEqualTo("10-10-2010");
    }

    @Test
    public void testDateCommandThrowsDateTimeParseException(){
        DateCommand command = exampleDateCommand();
        assertThatThrownBy(() -> {
            command.setValue("LOREMIPSUM");
        }).isInstanceOf(DateTimeParseException.class)
                .hasMessageContaining("");
    }

    @Test
    public void testTimeCommand(){
        TimeCommand command = exampleTimeCommand();
        command.setValue("10:10");

        assertThat(command.getValue()).isEqualTo("10:10");
    }

    @Test
    public void testTimeCommandThrowsDateTimeParseException(){
        TimeCommand command = exampleTimeCommand();
        assertThatThrownBy(() -> {
            command.setValue("LOREMIPSUM");
        }).isInstanceOf(DateTimeParseException.class)
                .hasMessageContaining("");
    }

    @Test
    public void testNumberCommand(){
        NumberCommand command = exampleNumberCommand();
        command.setValue(10);

        assertThat(command.getValue()).isEqualTo(10);
    }

    @Test
    public void testNumberCommandThrowsIllegalArgumentException(){
        NumberCommand command = exampleNumberCommand();
        assertThatThrownBy(() -> {
            command.setValue(null);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid Input");
    }

    @Test
    public void testBooleanCommand(){
        BooleanCommand commandOne = exampleBooleanCommand();
        BooleanCommand commandTwo = exampleBooleanCommand();
        commandOne.setValue("y");
        commandTwo.setValue("N");

        assertThat(commandOne.getValue()).isEqualTo("TRUE");
        assertThat(commandTwo.getValue()).isEqualTo("FALSE");
    }

    @Test
    public void testBooleanCommandThrowsIllegalArgumentException(){
        BooleanCommand command = exampleBooleanCommand();
        assertThatThrownBy(() -> {
            command.setValue(null);
            command.setValue("");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid Input");
    }




    private <V,T extends Command<V>> T exampleCommand(V value){
        return (T) new Command("name", "description", "String", "mode", "table", "none", new String[]{"none"}).setValue(value);
    }

    private StringCommand exampleStringCommand(){
        return new StringCommand("name", "description", "String", "mode", "table", "none", new String[]{"none"});
    }

    private HelpCommand exampleHelpCommand(){
        return new HelpCommand("name", "description", "String", "mode", "table", "none", new String[]{"none"});
    }

    private DateCommand exampleDateCommand(){
        return new DateCommand("name", "description", "String", "mode", "table", "none", new String[]{"none"});
    }

    private TimeCommand exampleTimeCommand(){
        return new TimeCommand("name", "description", "String", "mode", "table", "none", new String[]{"none"});
    }

    private BooleanCommand exampleBooleanCommand(){
        return new BooleanCommand("name", "description", "String", "mode", "table", "none", new String[]{"none"});
    }

    private NumberCommand exampleNumberCommand(){
        return new NumberCommand("name", "description", "String", "mode", "table", "none", new String[]{"none"});
    }

    private BasicCommand exampleBasicCommand(){
        return new BasicCommand("name","mode","table");
    }





}
