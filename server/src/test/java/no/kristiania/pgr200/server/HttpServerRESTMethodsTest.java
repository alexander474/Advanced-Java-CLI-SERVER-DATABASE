package no.kristiania.pgr200.server;

import no.kristiania.pgr200.common.HttpClientRequest;
import no.kristiania.pgr200.common.HttpClientResponse;
import no.kristiania.pgr200.db.TestDataSource;
import no.kristiania.pgr200.server.controllers.*;
import no.kristiania.pgr200.server.requesthandlers.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class HttpServerRESTMethodsTest {

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
    public void shouldReturnTalkController(){
        AbstractController controller = new HttpServerRouter().route("capi/talk", dataSource);
        assertThat(controller).isInstanceOf(TalkController.class);
    }

    @Test
    public void shouldReturnTrackController(){
        AbstractController controller = new HttpServerRouter().route("capi/track", dataSource);
        assertThat(controller).isInstanceOf(TrackController.class);
    }

    @Test
    public void shouldReturnConferenceController(){
        AbstractController controller = new HttpServerRouter().route("capi/conference", dataSource);
        assertThat(controller).isInstanceOf(ConferenceController.class);
    }

    @Test
    public void shouldReturnNullController(){
        AbstractController controller = new HttpServerRouter().route("capi/conf", dataSource);
        assertThat(controller).isNull();
    }

    @Test
    public void test10InsertRequestShouldInsertConference() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference", "POST", "application/json",
                "{\"title\":\"My conference\",\"description\":\"About my conference\",\"date_start\":\"09-10-2018\",\"date_end\":\"11-10-2018\"}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"date_start\":\"09-10-2018\",\"date_end\":\"11-10-2018\",\"title\":\"My conference\",\"description\":\"About my conference\",\"id\":1}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }


    @Test
    public void test11InsertRequestShouldInsertSecondConference() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference", "POST", "application/json",
                "{\"title\":\"My 2nd conference\",\"description\":\"About my conference\",\"date_start\":\"09-10-2018\",\"date_end\":\"11-10-2018\"}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"date_start\":\"09-10-2018\",\"date_end\":\"11-10-2018\",\"title\":\"My 2nd conference\",\"description\":\"About my conference\",\"id\":2}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test12InsertRequestShouldInsertTrack() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/track", "POST", "application/json",
                "{\"title\":\"My track\",\"description\":\"About my track\",\"track_conference_id\":1}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"track_conference_id\":1,\"title\":\"My track\",\"description\":\"About my track\",\"id\":1}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test13InsertRequestShouldInsertTalk() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/talk", "POST", "application/json",
                "{\"title\":\"My talk\",\"description\":\"About my talk\",\"talk_track_id\":1, \"location\":\"the room\", \"timeslot\":\"10:10:00 PM\"}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"talk_track_id\":1,\"timeslot\":\"10:10:00 PM\",\"title\":\"My talk\",\"description\":\"About my talk\",\"id\":1}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test14PutRequestShouldUpdateConference() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference/1", "PUT", "application/json",
                "{\"title\":\"My New conference\",\"description\":\"New about my conference\",\"date_start\":\"09-10-2018\",\"date_end\":\"11-10-2018\"}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"date_start\":\"09-10-2018\",\"date_end\":\"11-10-2018\",\"title\":\"My New conference\",\"description\":\"New about my conference\",\"id\":1}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test15PutRequestShouldUpdateTrack() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/track/1", "PUT", "application/json",
                "{\"title\":\"My updated track\",\"description\":\"About my updated track\",\"track_conference_id\":1}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"track_conference_id\":1,\"title\":\"My updated track\",\"description\":\"About my updated track\",\"id\":1}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test16InsertRequestShouldUpdateTalk() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/talk/1", "PUT", "application/json",
                "{\"title\":\"My updated talk\",\"description\":\"About my updated talk\",\"talk_track_id\":1, \"location\":\"the room\", \"timeslot\":\"10:30:00 PM\"}");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"talk_track_id\":1,\"timeslot\":\"10:30:00 PM\",\"title\":\"My updated talk\",\"description\":\"About my updated talk\",\"id\":1}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test16RetrieveRequestShouldRetrieveSingleRowConference() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference/1", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"date_start\":\"2018-10-09\",\"date_end\":\"2018-10-11\",\"title\":\"My New conference\",\"description\":\"New about my conference\",\"id\":1}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test17RetrieveRequestShouldRetrieveSingleRowTrack() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/track/1", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"track_conference_id\":1,\"title\":\"My updated track\",\"description\":\"About my updated track\",\"id\":1}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test17RetrieveRequestShouldRetrieveSingleRowTalk() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/talk/1", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isEqualTo("{\"talk_track_id\":1,\"timeslot\":\"10:30:00 PM\",\"title\":\"My updated talk\",\"description\":\"About my updated talk\",\"id\":1}");
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test18RetrieveAllRequestShouldRetrieveAllConference() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test19RetrieveAllRequestShouldRetrieveAllTrack() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/track", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test19RetrieveAllRequestShouldRetrieveAllTalk() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/talk", "GET");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void test19DeleteRequestShouldDeleteTalk() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/talk/1", "DELETE");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).contains("\"Element with id 1 successfully deleted.\"");
    }


    @Test
    public void test20DeleteRequestShouldDeleteTrack() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/track/1", "DELETE");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).contains("\"Element with id 1 successfully deleted.\"");
    }

    @Test
    public void test21DeleteRequestShouldDeleteConference() throws IOException{
        HttpClientRequest request = new HttpClientRequest("localhost", server.getPort(), "/capi/conference/1", "DELETE");
        HttpClientResponse response = request.execute();
        assertThat(response.getBody()).contains("\"Element with id 1 successfully deleted.\"");
    }
}
