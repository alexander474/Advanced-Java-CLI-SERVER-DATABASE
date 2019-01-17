package no.kristiania.pgr200.server.requesthandlers;

import no.kristiania.pgr200.server.HttpServerRequest;
import no.kristiania.pgr200.server.HttpServerResponse;

import java.io.IOException;

public interface HttpServerRequestHandler {

    boolean handleRequest(HttpServerRequest request, HttpServerResponse response) throws IOException;

}
