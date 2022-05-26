import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * This class define each line of data that the csv file has, creating an object for each one.
 * @author: Alejandro Rios.
 * @version: 25/05/2022. v2.0
 * @see: Class Data.
 */

public class DataFrame {
  // ArrayList that saves all the objects.
  public static ArrayList<Data> climateData = new ArrayList<Data>();
  /**
   * This method read each line of file. Save in a String line.
   * Split each data in an array. Then assigns the data to the appropriate data type.
   * Sends each data to the Constructor to create a new object, finally, adding to ArrayList.
   * @author: Alejandro Rios.
   * @exception FileNotFound if the file doesnt exists and Parse Conversion is the conversion has a problem.
   */
  public static void readCsv(String fileName) throws FileNotFoundException, ParseException {
    Scanner scanner = new Scanner(new File(fileName));
    String str;
    String[] data;
    while (scanner.hasNextLine()) {
      str = scanner.nextLine();
      str = str.replaceAll("\"", "");
      str = str.replaceAll(", ", "-");
      // The second parameter allows to read empty String.
      data = str.split(",", -1);

      String station = data[0];
      String name = data[1];
      Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data[2]);
      Double precipitation = tryParse(data[3], -1.0);
      Double temperatureAverage = tryParse(data[4], -1.0);
      Double temperatureMax = tryParse(data[5], -1.0);
      Double temperatureMin = tryParse(data[6], -1.0);

      climateData.add(new Data(station, name, date, precipitation, temperatureAverage, temperatureMax, temperatureMin));      
    }
  }
  /**
   * Instance method that checks if is able to convert a String into a Double. Avoiding an exception.
   * @author: Miguel Diaz.
   */
  public static Double tryParse(String value, Double defaultVal) {
    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException e) {
      return defaultVal;
    }
  }
  /**
   * Instance method with the Main menu. Where the user choose the city that want to analyze
   * @author: Alejandro Rios.
   */
  public static void menu() throws FileNotFoundException, ParseException {
    Scanner scan = new Scanner(System.in);
    
    /* System.out.println("Hello, in this program you will be able to read the information of the next files:"+"\n0. Bogotá"+"\n1. Medellín"+"\n2. San Francisco"+"\n3. Close system"); */
    System.out.println("Welcome. In this program you will be able to know different things about your city related to the weather.");
    System.out.println("Cities Menu. 🌎");
    System.out.println("0. Bogotá");
    System.out.println("1. Medellín");
    System.out.println("2. San Francisco");
    System.out.println("3. Close program");
    System.out.println("--------------------------------------------------------------------------------");
    boolean stateOfMenu = true;
      
    while(stateOfMenu){
      System.out.print("Enter number: ");
      int selectionMenu = scan.nextInt();
      System.out.println("--------------------------------------------------------------------------------");

      if(selectionMenu>-1 && selectionMenu<4) {
        switch(selectionMenu){
          case 0:
            readCsv("Bogota.csv");
            fileMenu();
            stateOfMenu = false;
            break;
          case 1:
            readCsv("Medellin.csv");
            fileMenu();
            stateOfMenu = false;
            break;
          case 2:
            readCsv("SanFrancisco.csv");
            fileMenu();
            stateOfMenu = false;
            break;
          case 3:
            System.out.println("System closed");
            stateOfMenu = false;
            break; 
        } 
        break;
      } else {
        System.out.println("Numero no valido");
        continue;
      }
    }
  }
  
  /**
   * Instance method with all the menu to manage the respective file and the software interaction.
   * @author: Miguel Diaz.
   */
  public static void fileMenu() throws FileNotFoundException, ParseException {
    Scanner scan = new Scanner(System.in);
    boolean access = true;
    System.out.println("--------------------------------------------------------------------------------");
    System.out.println("0. Print info of specific date");
    System.out.println("1. Filter data with statics");
    System.out.println("2. Return to Main Menu");
    System.out.println("3. Close system");
    System.out.println("--------------------------------------------------------------------------------");
    
    System.out.print("Enter number: ");
    int selectionFileMenu = scan.nextInt();
    while(access){
      if(selectionFileMenu>-1 && selectionFileMenu<4) {
        switch(selectionFileMenu){
          case 0:
            printInfoDate();
            break;
          case 1:
            filterStatistics();
            break;
          case 2:
            menu();
            break;
          case 3:
            System.out.println("Closed program");
            access = false;
            break;
        }       
        break;
      } else {
        System.out.println("Invalid number ⛔");
        continue;
      }
    }
  }
  /**
   * Instance method that prints the information of all items with the method toString().
   * @author: Alejandro Rios.
   */
  public static void printInfoDate() throws FileNotFoundException, ParseException {
    Scanner scan = new Scanner(System.in);
    boolean dataExists = true;
    System.out.println("============");
    System.out.println("What date do you want to analyze? (yyyy-MM-dd format)");
    String dateInfoInput = scan.next();
    Date dateInfo = new SimpleDateFormat("yyyy-MM-dd").parse(dateInfoInput);
    System.out.println("============");
    System.out.println("Climate Data");
    for (int i= 0; i < climateData.size(); i++) {
      if (dateInfo.equals(climateData.get(i).getDate()))
        System.out.print(climateData.get(i).toString());
      dataExists = false;
    }
    if (!dataExists) {
      System.out.println("Data no available");
    }
    System.out.println("============");
    System.out.println("Number of stations: " + climateData.size());
    System.out.println("============");
    fileMenu();
  }
  /**
   * Instance method that asks user for the respective filters. Date, range of date and statistic analysis.
   * Method sends three parameters to another method statistics
   * @see: Method statistics(Date dateReference, int selectionFilter, int selectionStatistics)
   * @author: Alejandro Rios.
   */
  public static void filterStatistics() throws FileNotFoundException, ParseException {
    Scanner scan = new Scanner(System.in);

    System.out.println("--------------------------------------------------------------------------------");
    System.out.println("Enter the date you want to reference. (In yyyy-MM-dd format)");
    System.out.print("Enter date: ");
    String dateReferenceInput = scan.next();
    Date dateReference = new SimpleDateFormat("yyyy-MM-dd").parse(dateReferenceInput);

    System.out.println("--------------------------------------------------------------------------------");
    System.out.println("Do you want to analyze the data 'before' or 'after' " + new SimpleDateFormat("dd-MM-yyyy").format(dateReference) + " ?");
    System.out.println("1. Before");
    System.out.println("2. After");
    System.out.print("Enter number: ");
    int selectionFilter = scan.nextInt();

    System.out.println("--------------------------------------------------------------------------------");
    System.out.println("Choose type of analysis, you want to do");
    System.out.println("1. Average");
    System.out.println("2. Maximum - Minimum");
    System.out.println("3. Variation and Desviation");
    System.out.print("Enter number: ");
    int selectionStatistics = scan.nextInt();

    statistics(dateReference, selectionFilter, selectionStatistics);
    fileMenu();
  }
  /**
   * Instance method that makes the road of analysis according to the user selection in the type of statistic.
   * Sends the dateReference and the selectionFilter of the past instance method to the statistics methods.
   * @author: Alejandro Rios, Jeronimo Guerrero.
   * @see: Instance method filterStatistics() - 
   */
  public static void statistics(Date dateReference, int selectionFilter, int selectionStatistics) {
    switch (selectionStatistics) {
      case 1:
        average(dateReference, selectionFilter);
        break;
      case 2:
        maximumMinimum(dateReference, selectionFilter);
        break;
      case 3:
        varianceDesviation(dateReference, selectionFilter);
        break;
    }
  }
  /**
   * Instance method that calculates the average in the respective range of date of precipitation and temperature.
   * @author: Alejandro Rios, Jeronimo Guerrero.
   */
  public static void average(Date dateReference, int selectionFilter){
    File file = new File("Results.txt");
    PrintWriter output = null;
    
    Double dataCounterPrecipitation = 0.0;
    Double dataAdderPrecipitation = 0.0;
    Double dataCounterTemperatureAverage = 0.0;
    Double dataAdderTempeatureAverage = 0.0;
    Double averagePrecipitation;
    Double averageTemperature;
    if (selectionFilter == 1) {
      for (int i = 0; i < climateData.size(); i++) {
        if (climateData.get(i).getPrecipitation() > -1.0 && climateData.get(i).getDate().before(dateReference)) {
          dataAdderPrecipitation+=climateData.get(i).getPrecipitation();
          dataCounterPrecipitation++;
        }
      }
      for (int i = 0; i < climateData.size(); i++) {
        if (climateData.get(i).getTemperatureAverage() > -1.0 && climateData.get(i).getDate().before(dateReference)) {
          dataAdderTempeatureAverage+=climateData.get(i).getTemperatureAverage();
          dataCounterTemperatureAverage++;
        }
      }
      averagePrecipitation = dataAdderPrecipitation / dataCounterPrecipitation;
      averageTemperature = dataAdderTempeatureAverage / dataCounterTemperatureAverage;
      System.out.println("Before " + new SimpleDateFormat("dd-MM-yyyy").format(dateReference));
      System.out.println("Average Precipitation: " + String.format("%.2f",averagePrecipitation) + " 🌧️");
      System.out.println("Average Temperature: " + String.format("%.2f",averageTemperature) + " ⛅");
      System.out.println("--------------------------------------------------------------------------------");
      try{
        PrintWriter writer = new PrintWriter(new FileOutputStream(new File("Result.txt"), true));
        writer.println("Before " + new SimpleDateFormat("dd-MM-yyyy").format(dateReference));
        writer.println("Average Precipitation:" + String.format("%.2f",averagePrecipitation) + " 🌧️"); 
        writer.println("Average Temperature: " + String.format("%.2f",averageTemperature) + " ⛅");
        writer.println("--------------------------------------------------------------------------------");
        writer.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
      
    } else if (selectionFilter == 2) {
      for (int i = 0; i < climateData.size(); i++) {
        if (climateData.get(i).getPrecipitation() > -1.0 && climateData.get(i).getDate().after(dateReference)) {
          dataAdderPrecipitation+=climateData.get(i).getPrecipitation();
          dataCounterPrecipitation++;
        }
      }
      for (int i = 0; i < climateData.size(); i++) {
        if (climateData.get(i).getTemperatureAverage() > -1.0 && climateData.get(i).getDate().after(dateReference)) {
          dataAdderTempeatureAverage+=climateData.get(i).getTemperatureAverage();
          dataCounterTemperatureAverage++;
        }
      }
      averagePrecipitation = dataAdderPrecipitation / dataCounterPrecipitation;
      averageTemperature = dataAdderTempeatureAverage / dataCounterTemperatureAverage;

    System.out.println("After " + new SimpleDateFormat("dd-MM-yyyy").format(dateReference));
    System.out.println("Average Precipitation is:" + String.format("%.2f",averagePrecipitation) + " 🌧️"); 
    System.out.println("Average Temperature is: " + String.format("%.2f",averageTemperature) + " ⛅");
    System.out.println("--------------------------------------------------------------------------------");
    try{
      PrintWriter writer = new PrintWriter(new FileOutputStream(new File("Result.txt"), true));
      writer.println("After " + new SimpleDateFormat("dd-MM-yyyy").format(dateReference));
      writer.println("Average Precipitation is:" + String.format("%.2f",averagePrecipitation) + " 🌧️"); 
      writer.println("Average Temperature is: " + String.format("%.2f",averageTemperature) + " ⛅");
      writer.println("--------------------------------------------------------------------------------");
      writer.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
    }
  }
  /**
   * Instance method that calculates the maximum and minimum precipitation and temperature in the specified range of date.
   * @author: Alejandro Rios, Jeronimo Guerrero.
   */
  public static void maximumMinimum(Date dateReference, int selectionFilter){
    Double maximumPrecipitation = climateData.get(0).getPrecipitation();
    Double minimumPrecipitation = climateData.get(0).getPrecipitation();
    Double maximumTemperature = climateData.get(0).getTemperatureMax();
    Double minimumTemperature = climateData.get(0).getTemperatureMin();
    
    if (selectionFilter == 1) {
      for (int i = 0; i < climateData.size(); i++) {
        if (maximumPrecipitation < climateData.get(i).getPrecipitation() && climateData.get(i).getPrecipitation() > -1.0 && climateData.get(i).getDate().before(dateReference)) {
          maximumPrecipitation = climateData.get(i).getPrecipitation();
        }
        if (minimumPrecipitation > climateData.get(i).getPrecipitation() && climateData.get(i).getPrecipitation() > -1.0 && climateData.get(i).getDate().before(dateReference)) {
          minimumPrecipitation = climateData.get(i).getPrecipitation();
        }
      }
      
      for (int i = 0; i < climateData.size(); i++) {
        if (maximumTemperature < climateData.get(i).getTemperatureMax() && climateData.get(i).getTemperatureMax() > -1.0 && climateData.get(i).getDate().before(dateReference)) {
          maximumTemperature = climateData.get(i).getTemperatureMax();
        }
        if (minimumTemperature > climateData.get(i).getTemperatureMin() && climateData.get(i).getTemperatureMin() > -1.0 && climateData.get(i).getDate().before(dateReference)) {
          minimumTemperature = climateData.get(i).getTemperatureMin();
        }
      }

      System.out.println("--------------------------------------------------------------------------------");
      System.out.println("Before " + new SimpleDateFormat("dd-MM-yyyy").format(dateReference));
      System.out.println("Maximum Precipitation is: " + maximumPrecipitation + " mm. 🌧️");
      System.out.println("Minumum Precipitation is: " + minimumPrecipitation + " mm. 🌧️");  
      System.out.println("Maximum Temperature is: " + maximumTemperature + " °C. ⛅");  
      System.out.println("Minimum Temperature is: " + minimumTemperature + " °C. ☁️");
      System.out.println("--------------------------------------------------------------------------------");
      try{
        PrintWriter writer = new PrintWriter(new FileOutputStream(new File("Result.txt"), true));
        writer.println("Before " + new SimpleDateFormat("dd-MM-yyyy").format(dateReference));
        writer.println("Maximum Precipitation is: " + maximumPrecipitation + " mm. 🌧️");
        writer.println("Minimum Precipitation is: " + minimumPrecipitation + " mm. 🌧️");  
        writer.println("Maximum Temperature is: " + maximumTemperature + " °C. ⛅");  
        writer.println("Minimum Temperature is: " + minimumTemperature + " °C. ☁️"); 
        writer.println("--------------------------------------------------------------------------------");
        writer.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
      
    } else if (selectionFilter == 2) {
      for (int i = 0; i < climateData.size(); i++) {
        if (maximumPrecipitation < climateData.get(i).getPrecipitation() && climateData.get(i).getPrecipitation() > -1.0 && climateData.get(i).getDate().after(dateReference)) {
          maximumPrecipitation = climateData.get(i).getPrecipitation();
        }
        if (minimumPrecipitation > climateData.get(i).getPrecipitation() && climateData.get(i).getPrecipitation() > -1.0 && climateData.get(i).getDate().after(dateReference)) {
          minimumPrecipitation = climateData.get(i).getPrecipitation();
        }
      }
      
      for (int i = 0; i < climateData.size(); i++) {
        if (maximumTemperature < climateData.get(i).getTemperatureMax() && climateData.get(i).getTemperatureMax() > -1.0 && climateData.get(i).getDate().after(dateReference)) {
          maximumTemperature = climateData.get(i).getTemperatureMax();
        }
        if (minimumTemperature > climateData.get(i).getTemperatureMin() && climateData.get(i).getTemperatureMin() > -1.0 && climateData.get(i).getDate().after(dateReference)) {
          minimumTemperature = climateData.get(i).getTemperatureMin();
        }
      }
      System.out.println("--------------------------------------------------------------------------------");
      System.out.println("After " + new SimpleDateFormat("dd-MM-yyyy").format(dateReference));
      System.out.println("Maximum Precipitation is: " + maximumPrecipitation + " mm. 🌧️");
      System.out.println("Minimum Precipitation is: " + minimumPrecipitation + " mm. 🌧️");  
      System.out.println("Maximum Temperature is: " + maximumTemperature + " °C. ⛅");  
      System.out.println("Minimum Temperature is: " + minimumTemperature + " °C. ☁️");
      System.out.println("--------------------------------------------------------------------------------");
      try{
        PrintWriter writer = new PrintWriter(new FileOutputStream(new File("Result.txt"), true));
        writer.println("After " + new SimpleDateFormat("dd-MM-yyyy").format(dateReference));
        writer.println("Maximum Precipitation is: " + maximumPrecipitation + " mm. 🌧️");
        writer.println("Minumum Precipitation is: " + minimumPrecipitation + " mm. 🌧️");  
        writer.println("Maximum Temperature is: " + maximumTemperature + " °C. ⛅");  
        writer.println("Minimum Temperature is: " + minimumTemperature + " °C. ☁️"); 
        writer.println("--------------------------------------------------------------------------------");
        writer.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
    }
  }

  public static void varianceDesviation(Date dateReference, int selectionFilter){
    File file = new File("Results.txt");
    PrintWriter output = null;

    Double dataCounterTemperature = 0.0;
    Double dataCounterPrecipitation = 0.0;
    Double adderPrecipitation = 0.0;
    Double adderTemperature = 0.0;
    Double averagePrecipitation = 0.0;
    Double averageTemperature = 0.0;
    Double desviationPrecipitation = 0.0;
    Double desviationTemperature = 0.0;
    Double variancePrecipitation = 0.0;
    Double varianceTemperature = 0.0;
    
      if (selectionFilter == 1) {
      for (int i = 0; i < climateData.size(); i++) {
        if (climateData.get(i).getPrecipitation() > -1.0 && climateData.get(i).getDate().before(dateReference)) {
          adderPrecipitation+=climateData.get(i).getPrecipitation();
          dataCounterPrecipitation++;
        }
      }
      for (int i = 0; i < climateData.size(); i++) {
        if (climateData.get(i).getTemperatureAverage() > -1.0 && climateData.get(i).getDate().before(dateReference)) {
          adderTemperature+=climateData.get(i).getTemperatureAverage();
          dataCounterTemperature++;
        }
      }
      averagePrecipitation = adderPrecipitation / dataCounterPrecipitation;
      averageTemperature = adderTemperature / dataCounterTemperature;

      for (int i = 0; i < climateData.size(); i++) {
        if (climateData.get(i).getPrecipitation() > -1.0 && climateData.get(i).getDate().before(dateReference)) {
          Double rangePrecipitation;
          rangePrecipitation = Math.pow(climateData.get(i).getPrecipitation() - averagePrecipitation, 2f);
          variancePrecipitation+=rangePrecipitation;
        }
      }
      for (int i = 0; i < climateData.size(); i++) {
        if (climateData.get(i).getTemperatureAverage() > -1.0 && climateData.get(i).getDate().before(dateReference)) {
          Double rangeTemperature;
          rangeTemperature = Math.pow(climateData.get(i).getTemperatureAverage() - averageTemperature, 2f);
          varianceTemperature+=rangeTemperature;
        }
      }

      variancePrecipitation = variancePrecipitation / dataCounterPrecipitation;
      varianceTemperature = varianceTemperature / dataCounterTemperature;
      desviationPrecipitation = Math.sqrt(variancePrecipitation);
      desviationTemperature = Math.sqrt(varianceTemperature);
      System.out.println("");
      System.out.println("Variance of Precipitation: " + String.format("%.2f",variancePrecipitation) + " 🌧️");
      System.out.println("Desviation of Precipitation: " + String.format("%.2f",desviationPrecipitation) + " 🌧️");
      System.out.println("Variance of Precipitation: " + String.format("%.2f",varianceTemperature) + " ⛅");
      System.out.println("Desviation of Precipitation: " + String.format("%.2f",desviationTemperature) + " ⛅");
      System.out.println("--------------------------------------------------------------------------------");
      try{
        PrintWriter writer = new PrintWriter(new FileOutputStream(new File("Result.txt"), true));
        writer.println("Variance of Precipitation: " + String.format("%.2f",variancePrecipitation) + " 🌧️");
        writer.println("Desviation of Precipitation: " + String.format("%.2f",desviationPrecipitation) + " 🌧️");
        writer.println("Variance of Precipitation: " + String.format("%.2f",varianceTemperature) + " ⛅");
        writer.println("Desviation of Precipitation: " + String.format("%.2f",desviationTemperature) + " ⛅");
        writer.println("--------------------------------------------------------------------------------");
        writer.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
      
    } else if (selectionFilter == 2) {
      for (int i = 0; i < climateData.size(); i++) {
        if (climateData.get(i).getPrecipitation() > -1.0 && climateData.get(i).getDate().after(dateReference)) {
          adderPrecipitation+=climateData.get(i).getPrecipitation();
          dataCounterPrecipitation++;
        }
      }
      for (int i = 0; i < climateData.size(); i++) {
        if (climateData.get(i).getTemperatureAverage() > -1.0 && climateData.get(i).getDate().after(dateReference)) {
          adderTemperature+=climateData.get(i).getTemperatureAverage();
          dataCounterTemperature++;
        }
      }
      averagePrecipitation = adderPrecipitation / dataCounterPrecipitation;
      averageTemperature = adderTemperature / dataCounterTemperature;

      for (int i = 0; i < climateData.size(); i++) {
        if (climateData.get(i).getPrecipitation() > -1.0 && climateData.get(i).getDate().after(dateReference)) {
          Double rangePrecipitation;
          rangePrecipitation = Math.pow(climateData.get(i).getPrecipitation() - averagePrecipitation, 2f);
          variancePrecipitation+=rangePrecipitation;
        }
      }
      for (int i = 0; i < climateData.size(); i++) {
        if (climateData.get(i).getTemperatureAverage() > -1.0 && climateData.get(i).getDate().after(dateReference)) {
          Double rangeTemperature;
          rangeTemperature = Math.pow(climateData.get(i).getTemperatureAverage() - averageTemperature, 2f);
          varianceTemperature+=rangeTemperature;
        }
      }

      variancePrecipitation = variancePrecipitation / dataCounterPrecipitation;
      varianceTemperature = varianceTemperature / dataCounterTemperature;
      desviationPrecipitation = Math.sqrt(variancePrecipitation);
      desviationTemperature = Math.sqrt(varianceTemperature);

      System.out.println("");
      System.out.println("Variance of Precipitation: " + String.format("%.2f",variancePrecipitation) + " 🌧️");
      System.out.println("Desviation of Precipitation: " + String.format("%.2f",desviationPrecipitation) + " 🌧️");
      System.out.println("Variance of Precipitation: " + String.format("%.2f",varianceTemperature) + " ⛅");
      System.out.println("Desviation of Precipitation: " + String.format("%.2f",desviationTemperature) + " ⛅");
      System.out.println("--------------------------------------------------------------------------------");
      try{
        PrintWriter writer = new PrintWriter(new FileOutputStream(new File("Result.txt"), true));
        writer.println("Variance of Precipitation: " + String.format("%.2f",variancePrecipitation) + " 🌧️");
        writer.println("Desviation of Precipitation: " + String.format("%.2f",desviationPrecipitation) + " 🌧️");
        writer.println("Variance of Precipitation: " + String.format("%.2f",varianceTemperature) + " ⛅");
        writer.println("Desviation of Precipitation: " + String.format("%.2f",desviationTemperature) + " ⛅");
        writer.println("--------------------------------------------------------------------------------");
        writer.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
    }
  }
}