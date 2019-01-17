CREATE TABLE IF NOT EXISTS Talk(
  talk_id SERIAL NOT NULL PRIMARY KEY,
  talk_title VARCHAR(50) NOT NULL,
  talk_description VARCHAR(255) NOT NULL,
  talk_location VARCHAR(20),
  talk_timeslot TIME NOT NULL,
  talk_track_id INT NOT NULL,
  FOREIGN KEY (talk_track_id) REFERENCES track (track_id) ON DELETE CASCADE
);
