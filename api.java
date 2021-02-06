import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class api {

    //api key 784c6e5fb55c291205025e163b54a785 for  for https://openweathermap.org/
    public static String getWeather(int zipCode) {
        // http://api.openweathermap.org/data/2.5/weather?zip=75080&units=imperial&appid=784c6e5fb55c291205025e163b54a785
        String result = "Wrong zip | or service is down";
        try {
            //create a new url object
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?zip=" + zipCode +
                    "&units=imperial&appid=784c6e5fb55c291205025e163b54a785");
            //create a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //set the request method
            connection.setRequestMethod("GET");
            //buffer for storing the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String tmp;
            //create an object to store read strings
            StringBuilder response = new StringBuilder();
            //buffer for storing the response from the server
            while ((tmp = reader.readLine()) != null) {
                //add the read line
                response.append(tmp);
            }
            //parse the read lines
            Gson gson = new Gson();
            Map<String, Object> gMap = gson.fromJson(response.toString(), Map.class);
            Map<String, Object> tMap = gson.fromJson(gMap.get("main").toString(), Map.class);

            JsonObject gObj = new Gson().fromJson(response.toString(), JsonObject.class);
            JsonArray wMap = gObj.get("weather").getAsJsonArray();
            JsonObject zero = wMap.get(0).getAsJsonObject();

            double minTemp = (Double) tMap.get("temp_min");
            double maxTemp = (Double) tMap.get("temp_max");
            String weatherMain = zero.get("main").getAsString();
            return "The weather’s going to be " + weatherMain + " with a high of " + maxTemp + " and a low of " + minTemp;
        } catch (IOException e) {
            System.out.println("Exception");
        }
        return result;
    }

    public static String getPrice(int currency) {
        try {
            //create a new url object
            URL url = new URL("https://steamcommunity.com/market/priceoverview/?currency="+currency+"&appid=578080&market_hash_name=EARLY%20BIRD%20KEY");
            //create a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //set the request method
            connection.setRequestMethod("GET");
            //buffer for storing the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String tmp;
            //create an object to store read strings
            StringBuilder response = new StringBuilder();
            //until the buffer is empty, read lines from it
            while ((tmp = reader.readLine()) != null) {
                //add the read line
                response.append(tmp);
            }
            //parse the read lines
            Gson gson = new Gson();
            Map<String, Object> gMap = gson.fromJson(response.toString(), Map.class);
            return (String) gMap.get("lowest_price");
        } catch (IOException e) {
            System.out.println("Exception");
        }
        //return -1000 if an exception occurs
        return null;
    }


    public static void main(String[] args) {
        System.out.println(getWeather(75080) + "\u2109");
        //0,1 dollars, 2 pounds, 3 euros
        System.out.println("EARLY BIRD KEY price: "+getPrice(2));
    }
}
