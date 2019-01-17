package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.common.DateHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CommandHandlerTest extends CommandHandler {

    @BeforeClass
    public static void initCommands() throws FileNotFoundException {
        ParseCommands.parseAllCommands();
    }

    @Test
    public void testInsertConferenceCommands(){
        List<Command> commands = exampleCommandInsert("conference\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        commands.stream().filter(c->c.getTable().toUpperCase().equals("CONFERENCE")).forEach(c->{
            assertThat(c.getName()).isNotNull();
            assertThat(c.getDescription()).isNotNull();
            assertThat(c.getTable().toUpperCase()).isEqualTo("CONFERENCE");
            assertThat(c.getMode().toUpperCase()).isEqualTo("INSERT");
            assertThat(c.getType()).isNotNull();
            assertThat(c.getValue()).isNotNull();
            assertThat(c.getSubQuestionName()).isNotNull();
            assertThat(c.getSubQuestionValue()).isNotNull();
        });

    }

    @Test
    public void testInsertTrackCommands(){
        List<Command> commands = exampleCommandInsert("track\r\ntitle\r\ndescription\r\n1");
        commands.stream().filter(c->c.getTable().toUpperCase().equals("TRACK")).forEach(c->{
            assertThat(c.getName()).isNotNull();
            assertThat(c.getDescription()).isNotNull();
            assertThat(c.getTable().toUpperCase()).isEqualTo("TRACK");
            assertThat(c.getMode().toUpperCase()).isEqualTo("INSERT");
            assertThat(c.getType()).isNotNull();
            assertThat(c.getValue()).isNotNull();
            assertThat(c.getSubQuestionName()).isNotNull();
            assertThat(c.getSubQuestionValue()).isNotNull();
        });
    }

    @Test
    public void testInsertTalkCommands(){
        List<Command> commands = exampleCommandInsert("talk\r\ntitle\r\ndescription\r\nlocation\r\n1\r\n10:10");
        commands.stream().filter(c->c.getTable().toUpperCase().equals("TALK")).forEach(c->{
            assertThat(c.getName()).isNotNull();
            assertThat(c.getDescription()).isNotNull();
            assertThat(c.getTable().toUpperCase()).isEqualTo("TALK");
            assertThat(c.getMode().toUpperCase()).isEqualTo("INSERT");
            assertThat(c.getType()).isNotNull();
            assertThat(c.getValue()).isNotNull();
            assertThat(c.getSubQuestionName()).isNotNull();
            assertThat(c.getSubQuestionValue()).isNotNull();
        });
    }

    @Test
    public void testRetrieveConferenceCommands(){
        List<Command> commands = exampleCommandRetrieve("conference\r\ny\r\ny\r\n");
        commands.stream().filter(c->c.getTable().toUpperCase().equals("CONFERENCE")).forEach(c->{
            assertThat(c.getName()).isNotNull();
            assertThat(c.getDescription()).isNotNull();
            assertThat(c.getTable().toUpperCase()).isEqualTo("CONFERENCE");
            assertThat(c.getMode().toUpperCase()).isEqualTo("RETRIEVE");
            assertThat(c.getType()).isNotNull();
            assertThat(c.getSubQuestionName()).isNotNull();
            assertThat(c.getSubQuestionValue()).isNotNull();
        });
    }


    @Test
    public void testUpdateConferenceCommands(){
        List<Command> commands = exampleCommandUpdate("conference\r\n1\r\ntitle\r\ndescription\r\n10-09-2005\r\n11-09-2005");
        commands.stream().filter(c->c.getTable().toUpperCase().equals("CONFERENCE")).forEach(c->{
            assertThat(c.getName()).isNotNull();
            assertThat(c.getDescription()).isNotNull();
            assertThat(c.getTable().toUpperCase()).isEqualTo("CONFERENCE");
            assertThat(c.getMode().toUpperCase()).isEqualTo("UPDATE");
            assertThat(c.getType()).isNotNull();
            assertThat(c.getValue()).isNotNull();
            assertThat(c.getSubQuestionName()).isNotNull();
            assertThat(c.getSubQuestionValue()).isNotNull();
        });
    }

    @Test
    public void testDeleteConferenceCommands(){
        List<Command> commands = exampleCommandDelete("conference\r\n1");
        commands.stream().filter(c->c.getTable().toUpperCase().equals("CONFERENCE")).forEach(c->{
            assertThat(c.getName()).isNotNull();
            assertThat(c.getDescription()).isNotNull();
            assertThat(c.getTable().toUpperCase()).isEqualTo("CONFERENCE");
            assertThat(c.getMode().toUpperCase()).isEqualTo("DELETE");
            assertThat(c.getType()).isNotNull();
            assertThat(c.getValue()).isNotNull();
            assertThat(c.getSubQuestionName()).isNotNull();
            assertThat(c.getSubQuestionValue()).isNotNull();
        });
    }

    @Test
    public void testHelpCommands(){
        CommandHandler interactiveHelp = exampleCommandHelp();
        List<Command> commands = interactiveHelp.readAllCommandsByTable("help", "help");
        assertThat(commands.size()>0);
        for(Command c :  commands){
            assertThat(c.getValue()).isNull();
            assertThat(c.getTable()).isEqualTo("help");
            assertThat(c.getMode()).isEqualTo("help");
            assertThat(c.getDescription()).isNotNull();
            assertThat(c.getType()).isEqualTo("help");
            assertThat(c.getSubQuestionValue()).isEqualTo("none");
            assertThat(c.getSubQuestionName()[0]).isEqualTo("none");
            assertThat(c.getValue()).isNull();
            assertThat(c).isInstanceOf(HelpCommand.class);
        }
        assertThat(interactiveHelp.readHelpCommands("help", "help")).isNotNull();
    }


    private Scanner writeToScanner(String message){
        return new Scanner( new ByteArrayInputStream(message.getBytes()));
    }

    private List<Command> exampleCommandInsert(String message){
        return new InteractiveInsert(writeToScanner(message)).start("INSERT");
    }
    private List<Command> exampleCommandRetrieve(String message){
        return new InteractiveRetrieve(writeToScanner(message)).start("RETRIEVE");
    }
    private List<Command> exampleCommandUpdate(String message){
        return new InteractiveUpdate(writeToScanner(message)).start("UPDATE");
    }
    private List<Command>  exampleCommandDelete(String message){
        return new InteractiveDelete(writeToScanner(message)).start("DELETE");
    }
    private CommandHandler exampleCommandHelp(){
        return new InteractiveHelp();
    }
}
