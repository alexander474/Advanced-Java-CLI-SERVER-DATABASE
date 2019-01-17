package no.kristiania.pgr200.db;

import no.kristiania.pgr200.db.AbstractDao;
import no.kristiania.pgr200.db.DataAccessObject;
import no.kristiania.pgr200.db.LocalDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TrackDao extends AbstractDao implements DataAccessObject<Track> {

    public TrackDao(DataSource dataSource) {
        super(dataSource);
    }

    public TrackDao() throws IOException {
        super(new LocalDataSource().createDataSource());
    }

    @Override
    public void create(Track track) throws SQLException {
        String createQuery = "INSERT INTO track(track_title, track_description, track_conference_id) VALUES(?,?,?)";
        long id = executeInsertAndReturnPrimaryKey(createQuery, new Object[]{
                track.getTitle(),
                track.getDescription(),
                track.getTrack_conference_id()
        });

        track.setId((int) id);
    }

    @Override
    public Track readOne(long id) throws SQLException {
        return readOne("SELECT track_id, track_title, track_description, track_conference_id FROM track where track_id = ?",
                this::mapToTrack, id);
    }

    @Override
    public List<Track> readAll() throws SQLException {
        return list("SELECT track_id, track_title, track_description, track_conference_id FROM track", this::mapToTrack);
    }

    @Override
    public void deleteOneById(long id) throws SQLException {
        String deleteQuery = "DELETE FROM track WHERE track_id = ?";
        deleteOneById(deleteQuery, id);
    }

    @Override
    public void updateOneById(Track track) throws SQLException {
        String updateQuery = "UPDATE track SET track_title = ?, track_description = ?, track_conference_id = ? WHERE track_id = ?";
        updateOneById(updateQuery, new Object[]{
                track.getTitle(),
                track.getDescription(),
                track.getTrack_conference_id(),
                track.getId()
        });
    }

    private Track mapToTrack(ResultSet rs) throws SQLException {
        return new Track(
                rs.getString("track_title"),
                rs.getString("track_description"),
                rs.getInt("track_id"),
                rs.getInt("track_conference_id")
        );
    }
}