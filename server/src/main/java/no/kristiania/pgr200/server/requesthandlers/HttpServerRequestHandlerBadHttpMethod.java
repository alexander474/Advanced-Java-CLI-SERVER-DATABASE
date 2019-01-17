package no.kristiania.pgr200.server.requesthandlers;

import no.kristiania.pgr200.server.HttpServerConfig;
import no.kristiania.pgr200.server.HttpServerRequest;
import no.kristiania.pgr200.server.HttpServerResponse;

import java.io.IOException;

public class HttpServerRequestHandlerBadHttpMethod implements HttpServerRequestHandler{

    @Override
    public boolean handleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException {
        if(HttpServerConfig.SUPPORTED_METHODS.contains(request.getHttpMethod())){
            return false;
        } else{
            response.setStatusCode(405);
            return true;
        }
    }
}
