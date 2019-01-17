package no.kristiania.pgr200.cli;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

public class ScannerHandlerTest {

    @Before
    public void insertAllCommands() throws FileNotFoundException {
        ParseCommands.parseAllCommands();
    }

    @Test
    public void shouldReturnIfCommand(){
        String command = "This is a command";
        Object result = ScannerHandler.scanInput(command);
        assertThat(result).isEqualTo("This is a command");
    }

    @Test
    public void shouldReturnU(){
        String command = "u";
        Object result = ScannerHandler.scanInput(command);
        assertThat(result).isEqualTo("u");
    }

    @Test
    public void shouldReturnInsert(){
        String command = "-i";
        Object result = ScannerHandler.scanInput(command);
        assertThat(result).isEqualTo("INSERT");
    }

    @Test
    public void shouldReturnRetrieve(){
        String command = "-r";
        Object result = ScannerHandler.scanInput(command);
        assertThat(result).isEqualTo("RETRIEVE");
    }

    @Test
    public void shouldReturnDelete(){
        String command = "-d";
        Object result = ScannerHandler.scanInput(command);
        assertThat(result).isEqualTo("DELETE");
    }

    @Test
    public void shouldReturnUpdate(){
        String command = "-u";
        Object result = ScannerHandler.scanInput(command);
        assertThat(result).isEqualTo("UPDATE");
    }

    @Test
    public void shouldReturnTable(){
        String command = "-t";
        Object result = ScannerHandler.scanInput(command);
        assertThat(result).isNull();
    }




}
