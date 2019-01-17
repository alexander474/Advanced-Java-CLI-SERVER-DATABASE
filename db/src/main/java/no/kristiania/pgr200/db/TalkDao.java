package no.kristiania.pgr200.db;

import no.kristiania.pgr200.db.AbstractDao;
import no.kristiania.pgr200.db.DataAccessObject;
import no.kristiania.pgr200.db.LocalDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TalkDao extends AbstractDao implements DataAccessObject<Talk> {

  public TalkDao(DataSource dataSource) {
    super(dataSource);
  }

  public TalkDao() throws IOException {
    super(new LocalDataSource().createDataSource());
  }

  @Override
  public void create(Talk talk) throws SQLException {
    //TODO(Håvard, Alexander, Jason): needs to implement based on conference id and room.
    String createQuery = "INSERT INTO Talk(talk_title, talk_description, talk_location, talk_track_id, talk_timeslot) VALUES(?,?,?,?,?)";
    long id = executeInsertAndReturnPrimaryKey(createQuery, new Object[]{
            talk.getTitle(),
            talk.getDescription(),
            talk.getTalk_location(),
            talk.getTalk_track_id(),
            talk.getTimeslot()
    });

    talk.setId((int) id);
  }

  @Override
  public Talk readOne(long id) throws SQLException {
    return readOne("SELECT talk_id, talk_title, talk_description, talk_track_id, talk_location, talk_timeslot FROM talk where talk_id = ?",
            this::mapToTalk, id);
  }

  @Override
  public List<Talk> readAll() throws SQLException {
    return list("SELECT talk_id, talk_title, talk_description, talk_track_id, talk_location, talk_timeslot FROM talk",
            this::mapToTalk);
  }

  @Override
  public void deleteOneById(long id) throws SQLException {
    String deleteQuery = "DELETE FROM talk WHERE talk_id = ?";
    deleteOneById(deleteQuery, id);
  }

  @Override
  public void updateOneById(Talk talk) throws SQLException {
    String updateQuery = "UPDATE talk SET talk_title = ?, talk_description = ?, talk_track_id = ?, talk_location = ?, talk_timeslot = ? WHERE talk_id = ?";
    updateOneById(updateQuery, new Object[]{
            talk.getTitle(),
            talk.getDescription(),
            talk.getTalk_track_id(),
            talk.getTalk_location(),
            talk.getTimeslot(),
            talk.getId()
    });
  }

  public Talk mapToTalk(ResultSet rs) throws SQLException {
    return new Talk(
            rs.getString("talk_title"),
            rs.getString("talk_description"),
            rs.getString("talk_location"),
            rs.getInt("talk_id"),
            rs.getInt("talk_track_id"),
            rs.getTime("talk_timeslot")
            );

  }
}