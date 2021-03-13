package zad2;

import org.json.JSONObject;

public class Weather {

    String weatherDesc;
    double temp;
    double humidity;
    double pressure;
    double wind;
    String city;
    String country;

    public Weather(String string, String string2, String string3) {

        this.city = string2;
        this.country = string3;
        JSONObject json = new JSONObject(string);

        String tmp = json.get("weather").toString();
        weatherDesc = tmp.substring(tmp.indexOf("\"description\":\"") + 15);
        weatherDesc = weatherDesc.substring(0, weatherDesc.indexOf("\""));

        tmp = json.get("main").toString();
        tmp = tmp.substring(8, tmp.indexOf(','));
        temp = Double.parseDouble(tmp);

        tmp = json.get("main").toString();
        tmp = tmp.substring(tmp.indexOf("\"humidity\":") + 11);
        tmp = tmp.substring(0, tmp.indexOf(","));
        humidity = Double.parseDouble(tmp);

        tmp = json.get("main").toString();
        tmp = tmp.substring(tmp.indexOf("\"pressure\":") + 11);
        tmp = tmp.substring(0, tmp.indexOf(","));
        pressure = Double.parseDouble(tmp);

        tmp = json.get("wind").toString();
        tmp = tmp.substring(tmp.indexOf("\"speed\":") + 8);
        tmp = tmp.replaceAll("[}]", ",");
        tmp = tmp.substring(0, tmp.indexOf(","));
        wind = Double.parseDouble(tmp);

    }

    public String toString() {
        return  "Description:" + weatherDesc +
                "\nTemperature:" + temp + " °C\n" +
                "Humidity:" + humidity + "%" +
                "\nPressure:" + pressure + " hPa" +
                "\nWind speed: " + wind + " m/s";

    }

}
