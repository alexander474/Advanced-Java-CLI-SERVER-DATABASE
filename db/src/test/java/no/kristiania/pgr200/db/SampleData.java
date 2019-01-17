package no.kristiania.pgr200.db;

import java.util.Random;

public class SampleData {

  private static Conference conference;
  private static Track track;
  private static Talk talk;
  private static Random random = new Random();

  protected static Conference sampleConference() {
    conference = new Conference();
    conference.setTitle(pickOne(new String[] { "John", "Paul", "George", "Ringo" }) + random.nextInt(200));
    conference.setDescription(pickOne(new String[] { "Lennon", "McCartney", "Harrison", "Starr" }));
    conference.setDate_start("09-12-2017");
    conference.setDate_end("09-12-2017");
    conference.setId(1);

    return conference;
  }

  protected static Track sampleTrack() {
    track = new Track();
    track.setId(1);
    track.setTitle(pickOne(new String[] {"Something", "Somewhere", "Someplace", "Location", "Bamalam"})+random.nextInt(200));
    track.setDescription(pickOne(new String[]{"The cool place", "Nowhere", "RocketMan", "Ladida"}));
    track.setTrack_conference_id(1);

    return track;
  }

  protected static Talk sampleTalk() {
    talk = new Talk();
    talk.setTitle(pickOne(new String[] {"Something", "Somewhere", "Someplace", "Location", "Bamalam"})+random.nextInt(200));
    talk.setDescription(pickOne(new String[]{"The cool place", "Nowhere", "RocketMan", "Ladida"}));
    talk.setTalk_location(pickOne(new String[]{"The cool place", "Nowhere", "RocketMan", "Ladida"}));
    talk.setTimeslot(pickOne(new String[]{"10:10", "12:10", "04:04", "02:10"}));
    talk.setTalk_track_id(1);
    return talk;
  }

  private static String pickOne(String[] alternatives) {
    return alternatives[random.nextInt(alternatives.length)];
  }
}