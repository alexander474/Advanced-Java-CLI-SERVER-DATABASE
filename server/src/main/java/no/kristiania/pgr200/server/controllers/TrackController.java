package no.kristiania.pgr200.server.controllers;

import com.google.gson.Gson;
import no.kristiania.pgr200.db.Track;
import no.kristiania.pgr200.db.TrackDao;
import no.kristiania.pgr200.server.HttpServerRequest;
import no.kristiania.pgr200.server.HttpServerResponse;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Map;

public class TrackController extends AbstractController {

    TrackDao dao;
    Gson gson = new Gson();

    public TrackController(DataSource dataSource) {
        dao = new TrackDao(dataSource);
    }

    @Override
    protected boolean dbInsert(HttpServerRequest request, HttpServerResponse response) throws SQLException{
        Track track = gson.fromJson(request.getBody(), Track.class);
        dao.create(track);
        response.setBody(gson.toJson(track));
        response.setStatusCode(200);
        return true;
    }

    @Override
    protected boolean dbUpdate(HttpServerRequest request, HttpServerResponse response, Map<String, Integer> params) throws SQLException{
        Track track = gson.fromJson(request.getBody(), Track.class);
        track.setId(params.get(":id"));
        dao.updateOneById(track);
        response.setBody(gson.toJson(track));
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