/**
 *
 *  @author Grzesiuk Bartosz S19982
 *
 */

package zad2;


import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.IOException;



public class Main  extends Application    {

    public static void main(String[] args) throws Exception {


        Service s = new Service("Polska");
        String weatherJson = s.getWeather("Warszawa");
        Double rate1 = s.getRateFor("USD");
        Double rate2 = s.getNBPRate();

       Application.launch(args);



    }

    public  void start(Stage stage) throws Exception {

       Service service = new Service("Polska");
        service.getWeather("Warszawa");
        startIt(service);


    }

public  void startIt (Service service ) throws IOException {

       Label label_country = new Label("COUNTRY : " + service.getCountry());
       Label label_city = new Label(" CITY : ");
       Double rate1 = service.getRateFor("USD");
       Double rate2 = service.getNBPRate();
       Label label_currency;

         if(service.getCountry().equals("Polska")) {
              label_currency = new Label("Currency rate : 1 "+ service.getCurrency()+  " =  " + rate2  +  " PLN  ");
         }else {
              label_currency = new Label("1 " + service.getCurrency() + " =  " + rate2 + " PLN  ");
         }

       Weather weather = new Weather(service.getWeather(service.getCity()),service.getCity(),service.getCountry() );
       Label label_weather = new Label(""+ weather.toString());



         Pane pane = new Pane();
         WebView webView = new WebView();
         TextField link = new TextField();
         Button button_change = new Button();
         WebEngine webEngine = webView.getEngine();

        label_country.setLayoutX(0);
        label_country.setLayoutY(0);
         label_city.setLayoutX(0);
         label_city.setLayoutY(30);

        label_currency.setLayoutX(0);
        label_currency.setLayoutY(60);

        label_weather.setLayoutX(250);
        label_weather.setLayoutY(0);

        label_city.setText(label_city.getText()+" " + service.getCity());

        link.setLayoutX(0);
        link.setLayoutY(0);

        button_change.setLayoutX(600);
        button_change.setLayoutY(0);
        button_change.setText("CHANGE DATA");


        webEngine.load("http://pl.wikipedia.org/wiki/"+service.getCity());
        webView.setLayoutX(0);
        webView.setLayoutY(100);
        pane.getChildren().addAll(webView,label_city,label_currency,label_weather,button_change,label_country);
        Scene scene = new Scene(pane,800,650);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        button_change.setOnAction( e-> {
            stage.close();
            changeCountry();
        });


}
    public void changeCountry() {

        Pane pane = new Pane();

        Button button_confirm = new Button("CONFIRM");

        TextField textField_country = new TextField();
        TextField textField_city = new TextField();

        Label label_country = new Label("Country :");
        Label label_city = new Label("City : ");

        textField_country.setLayoutX(151);
        textField_country.setLayoutY(72);

        textField_city.setLayoutX(151);
        textField_city.setLayoutY(116);

        label_country.setLayoutX(78);
        label_country.setLayoutY(76);

        label_city.setLayoutX(78);
        label_city.setLayoutY(120);

        button_confirm.setLayoutX(199);
        button_confirm.setLayoutY(164);

        pane.getChildren().addAll(textField_city,textField_country,label_city,label_country,button_confirm);

        Scene scene = new Scene(pane,344,251);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        button_confirm.setOnAction(e-> {

            Service s = new Service(textField_country.getText());
            s.getWeather(textField_city.getText());


            try {
                startIt(s);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            stage.close();
        });

   }

}


