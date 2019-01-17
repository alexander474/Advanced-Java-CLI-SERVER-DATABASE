package no.kristiania.pgr200.common;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateHandler {

  private static final String DATE_PATTERN = "dd-MM-yyyy";
  private static final String TIME_PATTERN = "HH:mm";
  public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
  public static final DateTimeFormatter TimeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);

  public DateHandler(){ }

  public static long convertDateToEpoch(String dateToConvert){
    //Passe inn en dato, få den konvertert til epoch long og returner longen
    long finalTime = 0;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);

    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC+1"));
    try {
      Date date = simpleDateFormat.parse(dateToConvert);
      finalTime = date.getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return finalTime;
  }

  public static Time parseTime(String value){
    return Time.valueOf(LocalTime.parse(value, DateTimeFormatter.ofPattern(TIME_PATTERN)));
  }
}
