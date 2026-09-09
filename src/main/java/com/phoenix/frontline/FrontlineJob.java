package com.phoenix.frontline;

public class FrontlineJob {

  private final String date;
  private final String time;
  private final String duration;
  private final String location;

  public FrontlineJob(
      String date,
      String time,
      String duration,
      String location) {

    this.date = date;
    this.time = time;
    this.duration = duration;
    this.location = location;
  }

  public String getDate() {
    return date;
  }

  public String getTime() {
    return time;
  }

  public String getDuration() {
    return duration;
  }

  public String getLocation() {
    return location;
  }

  public String getUniqueKey() {

    return date + "|" +
        time + "|" +
        duration + "|" +
        location;
  }

  @Override
  public String toString() {

    return "FrontlineJob{" +
        "date='" + date + '\'' +
        ", time='" + time + '\'' +
        ", duration='" + duration + '\'' +
        ", location='" + location + '\'' +
        '}';
  }
}