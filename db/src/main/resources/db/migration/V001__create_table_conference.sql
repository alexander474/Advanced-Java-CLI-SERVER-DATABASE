CREATE TABLE IF NOT EXISTS Conference(
  conference_id SERIAL NOT NULL PRIMARY KEY,
  conference_title VARCHAR(50) NOT NULL,
  conference_description VARCHAR(255) NOT NULL,
  conference_start_date DATE NOT NULL,
  conference_end_date DATE NOT NULL
);
