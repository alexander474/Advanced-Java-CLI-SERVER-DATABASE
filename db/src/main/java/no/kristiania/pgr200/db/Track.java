package no.kristiania.pgr200.db;

import java.util.Objects;

public class Track extends Entity {
    private int track_conference_id;

    public Track(String title, String description, int id, int track_conference_id) {
        super(title, description, id);
        this.track_conference_id = track_conference_id;
    }

    public Track() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrack_conference_id() {
        return track_conference_id;
    }

    public void setTrack_conference_id(int track_conference_id) {
        this.track_conference_id = track_conference_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return id == track.id &&
                track_conference_id == track.track_conference_id &&
                Objects.equals(title, track.title) &&
                Objects.equals(description, track.description);
    }

    @Override
    public String toString() {
        return "Track{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", track_conference_id=" + track_conference_id +
                '}';
    }
}
