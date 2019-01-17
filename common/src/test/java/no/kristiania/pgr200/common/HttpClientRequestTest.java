package no.kristiania.pgr200.common;

import static org.assertj.core.api.Assertions.assertThat;

import no.kristiania.pgr200.common.HttpClientRequest;
import no.kristiania.pgr200.common.HttpClientResponse;
import org.junit.Test;

import java.io.IOException;

public class HttpClientRequestTest {

    @Test
    public void shouldReadStatusCode() throws IOException {
        HttpClientRequest request = new HttpClientRequest("urlecho.appspot.com", 80, "/echo?status=200", "GET");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldReadOtherStatusCodes() throws IOException {
        HttpClientRequest request = new HttpClientRequest("urlecho.appspot.com", 80, "/echo?status=404", "GET");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void shouldHandlePostRequest() throws IOException {
        HttpClientRequest request = new HttpClientRequest("urlecho.appspot.com", 80, "/echo", "POST", "status=404");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldHandlePostRequestPartTwo() throws IOException {
        HttpClientRequest request = new HttpClientRequest("urlecho.appspot.com", 80, "/echo", "POST", "text/plain", "status=404");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldReadResponseHeaders() throws IOException {
        HttpClientRequest request = new HttpClientRequest("urlecho.appspot.com",
                80, "/echo?status=307&Location=http%3A%2F%2Fwww.google.com", "GET");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(307);
        assertThat(response.getHeader("Location")).isEqualTo("http://www.google.com");
    }

    @Test
    public void shouldReadResponseBody() throws IOException {
        HttpClientRequest request = new HttpClientRequest("urlecho.appspot.com",
                80, "/echo?body=Hello+world!", "GET");
        HttpClientResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello world!");
    }


    public static void main(String[] args) throws IOException {

    }


}
