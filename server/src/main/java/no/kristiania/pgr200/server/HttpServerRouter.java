package no.kristiania.pgr200.server;

import no.kristiania.pgr200.server.controllers.*;

import javax.sql.DataSource;

public class HttpServerRouter {

    public AbstractController route(String url, DataSource dataSource){
        if(url.startsWith("capi/conference")){
            return new ConferenceController(dataSource);
        }

        if(url.startsWith("capi/track")){
            return new TrackController(dataSource);
        }

        if(url.startsWith("capi/talk")){
            return new TalkController(dataSource);
        }

        return null;
    }
}
