package no.kristiania.pgr200.db;

import no.kristiania.pgr200.common.DateHandler;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ConferenceDao extends AbstractDao implements DataAccessObject<Conference> {

  public ConferenceDao(DataSource dataSource) {
    super(dataSource);
  }

  public ConferenceDao() throws IOException {
    super(new LocalDataSource().createDataSource());
  }

  @Override
  public void create(Conference conference) throws SQLException {
    String createQuery = "INSERT INTO conference(conference_title, conference_description, conference_start_date, conference_end_date) VALUES(?,?,?,?)";
    long id = executeInsertAndReturnPrimaryKey(createQuery, new Object[]{
            conference.getTitle(),
            conference.getDescription(),
            new Date(DateHandler.convertDateToEpoch(conference.getDate_start())),
            new Date(DateHandler.convertDateToEpoch(conference.getDate_end()))});

    conference.setId((int) id);
  }

  @Override
  public Conference readOne(long id) throws SQLException {
    return readOne("SELECT conference_id, conference_title, conference_description, conference_start_date, conference_end_date FROM conference where conference_id = ?",
            this::mapToConference, id);
  }

  @Override
  public List<Conference> readAll() throws SQLException {
    return list("SELECT conference_id, conference_title, conference_description, conference_start_date, conference_end_date FROM conference", this::mapToConference);
  }

  @Override
  public void deleteOneById(long id) throws SQLException {
    String deleteQuery = "DELETE FROM conference WHERE conference_id = ?";
    deleteOneById(deleteQuery, id);
  }

  @Override
  public void updateOneById(Conference conference) throws SQLException {
    String updateQuery = "UPDATE conference SET conference_title = ?, conference_description = ?, conference_start_date = ?, conference_end_date = ? WHERE conference_id = ?";
    updateOneById(updateQuery, new Object[]{
            conference.getTitle(),
            conference.getDescription(),
            new Date(DateHandler.convertDateToEpoch(conference.getDate_start())),
            new Date(DateHandler.convertDateToEpoch(conference.getDate_end())),
            conference.getId()
    });
  }

  private Conference mapToConference(ResultSet rs) throws SQLException {
    return new Conference(
            rs.getInt("conference_id"),
            rs.getString("conference_title"),
            rs.getString("conference_description"),
            rs.getString("conference_start_date"),
            rs.getString("conference_end_date"));
  }
}