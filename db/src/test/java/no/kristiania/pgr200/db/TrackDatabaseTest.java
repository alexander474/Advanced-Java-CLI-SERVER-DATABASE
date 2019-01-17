package no.kristiania.pgr200.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class TrackDatabaseTest {

  private Track track;
  private DataSource dataSource;
  private TrackDao trackDao;
  private ConferenceDao conferenceDao;
  private Conference conference;
  private TestDataSource testDataSource = new TestDataSource();

  @Before
  public void makeTrack(){
    this.dataSource = testDataSource.createDataSource();
    this.conferenceDao = new ConferenceDao(dataSource);
    this.trackDao = new TrackDao(dataSource);
    this.conference = SampleData.sampleConference();
    this.track = SampleData.sampleTrack();
  }

  @After
  public void resetTrack(){
    this.dataSource = null;
    this.track = null;
    this.trackDao = null;
    this.conferenceDao = null;
    this.conference = null;
    testDataSource.dropTables();
  }

  @Test
  public void shouldReturnCorrectTrackTitle() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);

    assertThat(trackDao.readOne(track.getId()).getTitle()).isEqualTo(track.getTitle());
  }

  @Test
  public void shouldFindSavedTrack() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);
    assertThat(trackDao.readAll()).contains(track);
  }

  @Test
  public void shouldCompareTrackFieldByField() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);
    assertThat(trackDao.readOne(track.getId()))
            .isEqualToComparingFieldByField(track);
  }

  @Test
  public void shouldReturnTrackIdMatchConferenceId() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);

    assertThat(conferenceDao.readAll()).contains(conference);
    assertThat(trackDao.readAll()).contains(track);
    assertThat(conferenceDao.readOne(conference.getId()).getId()).isEqualTo(trackDao.readOne(track.getId()).getTrack_conference_id());
  }

  @Test
  public void shouldUpdateTrackInTable() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);

    trackDao.updateOneById(new Track(
            "Maven room",
            "Everything related to Maven",
            track.getId(), conference.getId()));
    assertThat(trackDao.readOne(track.getId()).getTitle()).isEqualTo("Maven room");
    assertThat(trackDao.readOne(track.getId()).getDescription()).isEqualTo("Everything related to Maven");
  }

  @Test
  public void shouldDeleteTrackAndConferenceInTable() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);

    assertThat(conferenceDao.readAll()).contains(conference);
    assertThat(trackDao.readAll()).contains(track);
    conferenceDao.deleteOneById(conference.getId());
    trackDao.deleteOneById(track.getId());
    assertThat(trackDao.readAll()).doesNotContain(track);
  }

  @Test
  public void shouldReturnCorrectToString(){
    assertThat(track.toString())
      .isEqualTo("Track{title='"+track.getTitle()+"', description='"+track.getDescription()+"', id=1, track_conference_id=1}");
  }
}
