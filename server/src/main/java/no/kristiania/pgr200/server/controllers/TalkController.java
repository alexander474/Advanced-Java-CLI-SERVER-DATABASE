package no.kristiania.pgr200.server.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.kristiania.pgr200.db.Talk;
import no.kristiania.pgr200.db.TalkDao;
import no.kristiania.pgr200.server.HttpServerRequest;
import no.kristiania.pgr200.server.HttpServerResponse;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

public class TalkController extends AbstractController {

    TalkDao dao;
    Gson gson = new Gson();

    public TalkController(DataSource dataSource) {
        dao = new TalkDao(dataSource);
    }

    @Override
    protected boolean dbInsert(HttpServerRequest request, HttpServerResponse response) throws SQLException{
        Talk talk = gson.fromJson(request.getBody(), Talk.class);
        dao.create(talk);
        response.setBody(gson.toJson(talk));
        response.setStatusCode(200);
        return true;
    }

    @Override
    protected boolean dbUpdate(HttpServerRequest request, HttpServerResponse response, Map<String, Integer> params) throws SQLException{
        Talk talk = gson.fromJson(request.getBody(), Talk.class);
        talk.setId(params.get(":id"));
            dao.updateOneById(talk);
        response.setBody(gson.toJson(talk));
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
