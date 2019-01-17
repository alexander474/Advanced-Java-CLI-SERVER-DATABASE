package no.kristiania.pgr200.server;

import static org.assertj.core.api.Assertions.assertThat;

import no.kristiania.pgr200.common.HttpClientRequest;
import no.kristiania.pgr200.common.HttpClientResponse;
import no.kristiania.pgr200.db.TestDataSource;
import no.kristiania.pgr200.server.requesthandlers.*;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;

public class HttpServerTest {

    private static TestDataSource testDatasource = new TestDataSource();
    private static DataSource dataSource;
    private static HttpServerListener server;
    int port = 0;

    @BeforeClass
    public static void startServer() throws IOException {
        dataSource = testDatasource.createDataSource();
        server = new HttpServerListener(
                Arrays.asList(new HttpServerRequestHandlerCapi(dataSource),
                        new HttpServerRequestHandlerBadHttpMethod(),
                        new HttpServerRequestHandlerEcho(),
                        new HttpServerRequestHandlerURL()),
                new HttpServerParserRequest(),
                new HttpServerWriterResponse()
        );
        server.start(0);
    }

    @Test

    public void shouldReturnResource() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldWriteStatusCode() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/echo?status=200", "GET");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldReadOtherStatusCodes() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/echo?status=404", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void shouldReadResponseHeaders() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/echo?status=307&Location=http%3A%2F%2Fwww.kristiania.no", "GET");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(307);
        assertThat(response.getHeader("Location")).isEqualTo("http://www.kristiania.no");
    }

    @Test
     public void shouldReadResponseBody() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/echo?body=Hello+world!", "GET");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello world!");
    }

    @Test
    public void shouldEchoResponseBody() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/echo?body=Hello+Kristiania!", "GET");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello Kristiania!");
    }

    @Test
    public void shouldHandleEchoEmptyParam() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/echo?", "GET");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    public void shouldHandleEchoNoParams() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/echo", "GET");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    public void shouldWriteStatusCodePOST() throws IOException {
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/echo","POST",
                "status=200&body=hello+idiot");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("hello idiot");
    }

    @Test
    public void shouldHandleNoBodyPOST() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/echo",
                "POST", "");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    public void shouldRejectGETRequestWithInvalidPath() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/bogus", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    public void shouldRejectPOSTRequestWithGarbageBody() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/bogus","POST", "I+AM+A+BOGUS+POST+REQUEST");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    public void shouldRejectInvalidMethodwithValidPath() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/echo?body=hello+idiot", "PUT");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(405);
        assertThat(response.getBody()).isEqualTo("");
    }

    @Test
    public void shouldRejectDeleteMethod() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(),
                "/", "DELETE");
        HttpClientResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(405);
        assertThat(response.getBody()).isEqualTo("");
    }

}