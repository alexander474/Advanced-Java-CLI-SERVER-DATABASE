package no.kristiania.pgr200.io;
import no.kristiania.pgr200.cli.ExampleData;
import no.kristiania.pgr200.io.Request;
import no.kristiania.pgr200.io.RequestHandler;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHandlerTest {

    @Test
    public void shouldParsePOSTCommands(){
        RequestHandler requestHandler = new RequestHandler(ExampleData.getStringINSERTCommand());
        Request r = requestHandler.mapToRequest(0, "localhost");
        assertThat(r.getHostName()).isEqualTo("localhost");
        assertThat(r.getTable()).isEqualTo("track");
        assertThat(r.getMethod()).isEqualTo("POST");
        assertThat(r.getMode()).isEqualTo("insert");
        assertThat(r.getPort()).isEqualTo(0);
        assertThat(r.getId()).isNull();
        assertThat(r.getPath()).isEqualTo("/capi/track");
        assertThat(r.getBody()).isNotNull();
        assertThat(r.getBody()).isEqualTo("{\"name\":\"value\"}");
        assertThat(r).isInstanceOf(Request.class);
    }

    @Test
    public void shouldParseGETCommands(){
        RequestHandler requestHandler = new RequestHandler(ExampleData.getStringGETCommand());
        Request r = requestHandler.mapToRequest(0, "localhost");
        assertThat(r.getHostName()).isEqualTo("localhost");
        assertThat(r.getTable()).isEqualTo("track");
        assertThat(r.getMethod()).isEqualTo("GET");
        assertThat(r.getMode()).isEqualTo("retrieve");
        assertThat(r.getPort()).isEqualTo(0);
        assertThat(r.getId()).isNotNull();
        assertThat((long)r.getId()).isEqualTo(1);
        assertThat(r.getPath()).isEqualTo("/capi/track/1");
        assertThat(r.getBody()).isNotNull();
        assertThat(r.getBody()).isEqualTo("{\"id\":1}");
        assertThat(r).isInstanceOf(Request.class);
    }

    @Test
    public void shouldParseCommand(){
        RequestHandler requestHandler = new RequestHandler("track","retrieve", null);
        Request r = requestHandler.mapToRequest(0, "localhost");
        assertThat(r.getHostName()).isEqualTo("localhost");
        assertThat(r.getTable()).isEqualTo("track");
        assertThat(r.getMethod()).isEqualTo("GET");
        assertThat(r.getMode()).isEqualTo("retrieve");
        assertThat(r.getPort()).isEqualTo(0);
    }

    @Test
    public void shouldParseBasicCommand(){
        RequestHandler requestHandler = new RequestHandler("RESET","RESET");
        Request r = requestHandler.mapToRequest(0, "localhost");
        System.out.println(r.toString());
        assertThat(r.getHostName()).isEqualTo("localhost");
        assertThat(r.getTable()).isEqualTo("RESET");
        assertThat(r.getMethod()).isEqualTo("POST");
        assertThat(r.getMode()).isEqualTo("RESET");
        assertThat(r.getPath()).isEqualTo("/capi/RESET");
        assertThat(r.getPort()).isEqualTo(0);
    }
}
