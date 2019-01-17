package no.kristiania.pgr200.server.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.kristiania.pgr200.db.Conference;
import no.kristiania.pgr200.db.ConferenceDao;
import no.kristiania.pgr200.server.HttpServerRequest;
import no.kristiania.pgr200.server.HttpServerResponse;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

public class ConferenceController extends AbstractController {

    private ConferenceDao dao;
    private Gson gson = new Gson();

    public ConferenceController(DataSource dataSource) {
        dao = new ConferenceDao(dataSource);
    }

    @Override
    protected boolean dbInsert(HttpServerRequest request, HttpServerResponse response) throws SQLException{
        Conference conference = gson.fromJson(request.getBody(), Conference.class);
        dao.create(conference);
        response.setBody(gson.toJson(conference));
        response.setStatusCode(200);
        return true;
    }

    @Override
    protected boolean dbUpdate(HttpServerRequest request, HttpServerResponse response, Map<String, Integer> params) throws SQLException{
        Conference conference = gson.fromJson(request.getBody(), Conference.class);
        conference.setId(params.get(":id"));
        dao.updateOneById(conference);
        response.setBody(gson.toJson(conference));
        response.setStatusCode(200);
        return true;
    }

    @Override
    protected boolean dbDelete(HttpServerResponse response, Map<String, Integer> params) throws SQLException{
        dao.deleteOneById(Long.valueOf(params.get(":id")));
        response.setBody(gson.toJson("Element with id " + params.get(":id") + " successfully deleted."));
        response.setStatusCode(200);
        return true;
    }

    @Override
    protected boolean dbRetrieve(HttpServerResponse response, Map<String, Integer> params) throws SQLException{
        if(params.containsKey(":id")) {
            response.setBody(gson.toJson(dao.readOne(Long.valueOf(params.get(":id")))));
        } else{
            response.setBody(gson.toJson(dao.readAll()));
        }
        response.setStatusCode(200);
        return true;
    }
}