package no.kristiania.pgr200.cli;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class parseCommandsTest {

    @BeforeClass
    public static void initCommands() throws FileNotFoundException {
        //ParseCommands.commandFilePath = "client/src/test/resources/commands.json";
        ParseCommands.parseAllCommands();
    }


    @Test
    public void insertAllCommands(){
        List<Command> commands = ParseCommands.parse("conferences", "insert");
        for(Command c : commands){
            assertThat(c.getTable()).isEqualTo("conferences");
            assertThat(c.getMode()).isEqualTo("insert");
            assertThat(c).isInstanceOf(Command.class);
        }

    }

    @Test
    public void shouldParseConferenceCommands(){
        List<Command> commands = ParseCommands.parse("conferences", "insert");
        for(Command c : commands){
            assertThat(c.getTable()).isEqualTo("conferences");
            assertThat(c.getMode()).isEqualTo("insert");
            assertThat(c).isInstanceOf(Command.class);
        }
    }

    @Test
    public void shouldReturnStringAsAlternatives(){
        assertThat(ParseCommands.checkForAlternativeTable("con")).isEqualTo("CONFERENCE?");
        assertThat(ParseCommands.checkForAlternativeTable("tal")).isEqualTo("TALK?");
        assertThat(ParseCommands.checkForAlternativeTable("tra")).isEqualTo("TRACK?");
        assertThat(ParseCommands.checkForAlternativeTable("t")).contains("TALK").contains("TRACK");
    }

    @Test
    public void shouldReturnNullForNoAlternatives(){
        assertThat(ParseCommands.checkForAlternativeTable("loremIpsumRandom")).isNull();
        assertThat(ParseCommands.checkForAlternativeTable("123")).isNull();
        assertThat(ParseCommands.checkForAlternativeTable("--")).isNull();
    }

    @Test
    public void shouldReturnTrueIfTableExists(){
        assertThat(ParseCommands.checkForTable("conference")).isTrue();
        assertThat(ParseCommands.checkForTable("talk")).isTrue();
        assertThat(ParseCommands.checkForTable("track")).isTrue();
        assertThat(ParseCommands.checkForTable("help")).isTrue();
    }

    @Test
    public void shouldThrowErrIfTableNotExists(){
        assertThatThrownBy(() -> {
            ParseCommands.checkForTable("loremIpsum");
            ParseCommands.checkForTable("randomTableThatIsNotDefined");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Please enter a valid table!");
    }

    @Test
    public void shouldParseHelpCommands(){
        List<Command> commands = ParseCommands.parse("help", "help");
        for(Command c : commands){
            assertThat(c.getTable()).isEqualTo("help");
            assertThat(c.getMode()).isEqualTo("help");
            assertThat(c).isInstanceOf(HelpCommand.class);
        }
    }
}
