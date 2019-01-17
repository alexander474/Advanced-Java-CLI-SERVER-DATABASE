package no.kristiania.pgr200.db;

import java.util.Objects;

public class Conference extends Entity {
  private String date_start, date_end;

  public Conference(int id, String title, String description, String date_start, String date_end) {
    super(title, description, id);
    setDate_start(date_start);
    setDate_end(date_end);
  }

  public Conference(){}

  public String getDate_start() {
    return date_start;
  }

  public void setDate_start(String date_start){
    this.date_start = date_start;
  }

  public String getDate_end() {
    return date_end;
  }


  public void setDate_end(String date_end){
    this.date_end = date_end;
  }
  @Override
  public String toString() {
    return getClass().getSimpleName()+"{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", date_start=" + date_start +
            ", date_end=" + date_end +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Conference that = (Conference) o;
    return id == that.id &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description);
  }

}
