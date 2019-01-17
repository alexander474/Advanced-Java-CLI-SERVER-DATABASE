package no.kristiania.pgr200.db;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Time;

import static org.assertj.core.api.Assertions.assertThat;

public class TalkDatabaseTest {

  private Talk talk;
  private TalkDao talkDao;
  private DataSource dataSource;
  private TestDataSource testDatasource = new TestDataSource();
  private TrackDao trackDao;
  private ConferenceDao conferenceDao;
  private Track track;
  private Conference conference;
  private Conference conferenceEquals;

  @Before
  public void makeTalk(){
    this.conference = SampleData.sampleConference();
    this.conferenceEquals = SampleData.sampleConference();
    this.track = SampleData.sampleTrack();
    this.talk = SampleData.sampleTalk();
    this.dataSource = testDatasource.createDataSource();
    this.conferenceDao = new ConferenceDao(dataSource);
    this.trackDao = new TrackDao(dataSource);
    this.talkDao = new TalkDao(dataSource);
  }

  @After
  public void resetTalk(){
    this.dataSource = null;
    this.track = null;
    this.conference = null;
    this.talk = null;
    this.conferenceDao = null;
    this.trackDao = null;
    this.talkDao = null;
    testDatasource.dropTables();
  }

  @Test
  public void shouldReturnCorrectTalkTitle() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);
    talkDao.create(talk);

    assertThat(talkDao.readOne(track.getId()).getTitle()).isEqualTo(talk.getTitle());
  }

  @Test
  public void shouldFindTalkInDatabase() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);
    talkDao.create(talk);
    assertThat(talkDao.readAll()).contains(talk);
  }

  @Test
  public void shouldCompareTalkFieldByField() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);
    talkDao.create(talk);
    assertThat(talkDao.readOne(talk.getId()))
            .isEqualToComparingOnlyGivenFields(talk);
  }

  @Test
  public void shouldReturnTalkIdMatchTrackIdMatchConferenceId() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);
    talkDao.create(talk);

    assertThat(conferenceDao.readAll()).contains(conference);
    assertThat(trackDao.readAll()).contains(track);
    assertThat(talkDao.readAll()).contains(talk);
    assertThat(conferenceDao.readOne(conference.getId()).getId()).isEqualTo(trackDao.readOne(track.getId()).getTrack_conference_id());
    assertThat(trackDao.readOne(track.getId()).getId()).isEqualTo(talkDao.readOne(talk.getId()).getTalk_track_id());
  }



  @Test
  public void shouldUpdateTalkInTable() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);
    talkDao.create(talk);

    talkDao.updateOneById(new Talk("History of Java", "The long walk over how Java works", "Maven room", talk.getId(), track.getId(), new Time(System.currentTimeMillis())));
    assertThat(talkDao.readOne(talk.getId()).getTitle()).isEqualTo("History of Java");
    assertThat(talkDao.readOne(talk.getId()).getDescription()).isEqualTo("The long walk over how Java works");
  }

  @Test
  public void shouldDeleteTalkInTable() throws SQLException {
    conferenceDao.create(conference);
    trackDao.create(track);
    talkDao.create(talk);

    assertThat(conferenceDao.readAll()).contains(conference);
    assertThat(trackDao.readAll()).contains(track);
    assertThat(talkDao.readAll()).contains(talk);
    talkDao.deleteOneById(talk.getId());
    assertThat(talkDao.readAll()).doesNotContain(talk);
  }

  @Test
  public void shouldReturnCorrectToString(){
    assertThat(talk.toString())
            .isEqualTo("Talk{title='"+talk.getTitle()+"', description='"+talk.getDescription()+"', talk_location='"+talk.getTalk_location()+"', id="+talk.getId()+", talk_track_id=1, timeslot="+talk.getTimeslot()+"}");
  }

  @Test
  public void shouldReturnCorrectHashCode(){
    Assert.assertFalse(conference.hashCode() == conferenceEquals.hashCode());
  }
}