package application;

public class hrtoon {
  private int id;
  private String title;
  private String releaseDate;
  private String dow;
  private String type;
  private int easterEggs;
  private String runtime;

  public hrtoon(int id, String releaseDate, String dow, String type, String title, int easterEggs,
      String runtime) {
    this.id = id;
    this.title = title;
    this.releaseDate = releaseDate;
    this.dow = dow;
    this.type = type;
    this.easterEggs = easterEggs;
    this.runtime = runtime;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getDow() {
    return dow;
  }

  public void setDow(String dow) {
    this.dow = dow;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getEasterEggs() {
    return easterEggs;
  }

  public void setEasterEggs(int easterEggs) {
    this.easterEggs = easterEggs;
  }

  public String getRuntime() {
    return runtime;
  }

  public void setRuntime(String runtime) {
    this.runtime = runtime;
  }
}
