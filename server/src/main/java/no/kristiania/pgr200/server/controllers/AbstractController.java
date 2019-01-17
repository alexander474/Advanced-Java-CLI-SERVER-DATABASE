package no.kristiania.pgr200.server.controllers;

import no.kristiania.pgr200.server.HttpServerRequest;
import no.kristiania.pgr200.server.HttpServerResponse;

import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractController {

    public boolean handleRequest(HttpServerRequest request, HttpServerResponse response, Map<String, Integer> params){
        try {
            switch (request.getHttpMethod()) {
                case "POST":
                    if (dbInsert(request, response)) {
                        return true;
                    }
                    break;
                case "PUT":
                    if (dbUpdate(request, response, params)) {
                        return true;
                    }
                    break;
                case "DELETE":
                    if (dbDelete(response, params)) {
                        return true;
                    }
                    break;
                case "GET":
                    if (dbRetrieve(response, params)) {
                        return true;
                    }
                    break;
                default:
                    return false;
            }
            return true;
        } catch(SQLException e){
            return false;
        }
    }
    abstract boolean dbInsert(HttpServerRequest request, HttpServerResponse response) throws SQLException;

    abstract boolean dbUpdate(HttpServerRequest request, HttpServerResponse response, Map<String, Integer> params) throws SQLException;

    abstract boolean dbDelete(HttpServerResponse response, Map<String, Integer> params) throws SQLException;

    abstract boolean dbRetrieve(HttpServerResponse response, Map<String, Integer> params) throws SQLException;


}