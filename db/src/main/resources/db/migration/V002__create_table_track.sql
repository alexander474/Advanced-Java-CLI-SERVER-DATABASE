CREATE TABLE IF NOT EXISTS Track(
  track_id SERIAL NOT NULL PRIMARY KEY,
  track_title VARCHAR(50) NOT NULL,
  track_description VARCHAR(255) NOT NULL,
  track_conference_id INT NOT NULL,
  FOREIGN KEY (track_conference_id) REFERENCES conference (conference_id) ON DELETE CASCADE
);