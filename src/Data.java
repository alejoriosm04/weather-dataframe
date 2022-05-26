import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * This class creates an Object of Data type.
 * @author: Miguel Diaz, Alejandro Rios.
 * @version: 23/05/2022
 */
public class Data {
  // Attributes of the object. Each attribute is the data that the file has for each line.
  private String station;
  private String name;
  private Date date;
  private double precipitation;
  private double temperatureAverage;
  private double temperatureMax;
  private double temperatureMin;

  /**
   * Constructor for the Data object.
   * @author: Miguel Diaz, Alejandro Rios.
   */

  public Data(){}
  
  public Data(String station, String name, Date date, double precipitation, double temperatureAverage, double temperatureMax, double temperatureMin) {
    this.station = station;
    this.name = name;
    this.date = date;
    this.precipitation = precipitation;
    this.temperatureAverage = temperatureAverage;
    this.temperatureMax = temperatureMax;
    this.temperatureMin = temperatureMin;
  }
  // Getters and setters of each attribute
  
  public String getStation() {
    return this.station;
  }

  public String getName() {
    return this.name;
  }

  public Date getDate() {
    return this.date;
  }

  public double getPrecipitation() {
    return this.precipitation;
  }

  public double getTemperatureAverage() {
    return this.temperatureAverage;
  }

  public double getTemperatureMax() {
    return this.temperatureMax;
  }

  public double getTemperatureMin() {
    return this.temperatureMin;
  }
  
  public void setStation(String station) {
    this.station = station;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setPrecipitation(double precipitation) {
    this.precipitation = precipitation;
  }

  public void setTemperatureAverage(double temperatureAverage) {
    this.temperatureAverage = temperatureAverage;
  }

  public void setTemperatureMax(double temperatureMax) {
    this.temperatureMax = temperatureMax;
  }

  public void setTemperatureMin(double temperatureMin) {
    this.temperatureMin = temperatureMin;
  }

  public String toString() {
    return "Station: " + station + "\n" +
            "Name: " + name + "\n" +
            "Date: " + new SimpleDateFormat("MM-dd-yyyy").format(this.getDate()) + "\n" +
            "Precipitation: " + precipitation + " mm 🌧️" + "\n" +
            "Temperature Average: " + temperatureAverage + " C° ⛅" + "\n" +
            "Temperature Max: " + temperatureMax + " C° 🌤️" + "\n" +
            "Temperature Min: " + temperatureMin + " C° ☁️" + "\n";
  }
}
