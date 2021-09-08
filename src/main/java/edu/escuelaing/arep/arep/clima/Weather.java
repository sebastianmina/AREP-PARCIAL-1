package edu.escuelaing.arep.arep.clima;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class Weather {

    private String key;

    /**
     * Metodo que genera el json al introducir la ciudad y llave especifica
     *
     * @param city tipo String
     * @return json con la informacion del clima en esa ciudad
     * @throws Exception
     */
    public String getWeather(String city) throws ApiException {
        key = "5f69fcdcf0d46474fcb513e8dab75a65";
        try{
            URL clima = new URL("/api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key);
            HttpURLConnection connection = (HttpURLConnection) clima.openConnection();
            connection.setRequestMethod("GET");
            StringBuffer res = null;
            int responseCode = connection.getResponseCode();

            if (responseCode==HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine = in.readLine();
                res = new StringBuffer();
                while (inputLine!=null) {
                    res.append(inputLine);
                    inputLine = in.readLine();
                }

                in.close();
            }
            return String.valueOf(res);
        }catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }
}
