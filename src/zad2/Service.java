package zad2; /**
 *
 *  @author Grzesiuk Bartosz S19982
 *
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONObject;

public class Service {

    private String country;
    private String key;
    private String iso;
    private String currency;
    private String city;
    private String currencyAdded;

    public String getCurrencyAdded() {
        return currencyAdded;
    }

    public void setCurrencyAdded(String currencyAdded) {
        this.currencyAdded = currencyAdded;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Service(String string) {

        city = new String();
        country = string;
        key = ("2867373a2d746742247a6ad1755c8376");
        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }
        iso = countries.get(country);

        try {
            currency = Currency.getInstance(new Locale("", iso)).getCurrencyCode();


            setCurrency(currency);
        } catch (Exception e) {
            System.err.println("Podany błedny kraj!");
            System.exit(0);
        }

    }

   public double getRateFor(String string) {
       System.out.println("waluta dodana " + string);


       if(currency.equals(string))
           return 1;
      if(currency.equals("EUR"))
          return getNBPRate();

       StringBuilder stb = new StringBuilder();
       JSONObject json = new JSONObject(connect("https://api.exchangeratesapi.io/latest?symbols=" + string + "," +currency));
      String tmp = new String(json.get("rates").toString());
       tmp = tmp.replaceAll("[^0-9.]","");
       stb.append(tmp);
       stb.delete(6,stb.length());
       tmp=stb.toString();
       setCurrencyAdded(string);
        return Double.parseDouble(tmp);

   }

    public String getWeather(String string) {

        city = string;
        JSONObject json = new JSONObject(connect(
                "http://api.openweathermap.org/data/2.5/weather?q=" + string + "," + iso + "&APPID=" + key + "&units=metric"));

        return json.toString();

    }

    public double getNBPRate() {

        StringBuilder stb = new StringBuilder();
        if(!currency.equals("PLN")) {
            String tmp = connect("http://www.nbp.pl/kursy/kursya.html");
            if(!tmp.contains(currency)) {
                tmp = connect("http://www.nbp.pl/kursy/kursyb.html");
                tmp = tmp.substring(tmp.indexOf(currency) + 51);
            } else
                tmp = tmp.substring(tmp.indexOf(currency) + 48);

            tmp = tmp.substring(0, tmp.indexOf('<'));
            tmp = tmp.replaceAll(",", ".");
            stb.append(tmp);
            stb.delete(0,17);
            tmp = stb.toString();

            return Double.parseDouble(tmp);
        }
        return 1;

    }

    public String connect(String string) {

        try {
            HttpURLConnection con = (HttpURLConnection) (new URL(string)).openConnection();
            con.setRequestMethod("GET");
            con.connect();
            StringBuffer buffer = new StringBuffer();
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;

            while ((line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();

            return buffer.toString();
        } catch(Exception e) {}

        return "";

    }

    public String getCountry() {
        return country;

    }

    public String getCity() {
        return city;

    }

}