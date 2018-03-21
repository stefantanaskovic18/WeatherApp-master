package dogadjaji;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import java.net.*;

public class KlikPrikaziPrognozu implements ActionListener {

    private JComboBox poljeGrad;
    private JTextArea poljePrikaza;

    public KlikPrikaziPrognozu(JComboBox poljeGrad, JTextArea poljePrikaza) {
        this.poljeGrad = poljeGrad;
        this.poljePrikaza = poljePrikaza;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(new FileReader("src\\cities.json"));
            for (Object o : array) {
                JSONObject grad = (JSONObject) o;
                if (grad.get("name").toString().equals(poljeGrad.getSelectedItem().toString())) {
                    String imeGrada = (String) grad.get("name");
                    String idGrada = (String) grad.get("geonameid");
                    System.out.println(imeGrada + ", " + idGrada);
                    String domen = "http://api.openweathermap.org/data/2.5/weather?q=" + imeGrada + "&appid=a15af71f50267b7fd49350cf4d87d758";
                    URL adresa = new URL(domen);
                    BufferedReader bf = new BufferedReader(new InputStreamReader(adresa.openStream()));
                    Object objectWeather = parser.parse(bf);

                    JSONObject weatherCondition = (JSONObject) objectWeather;
                    JSONArray weatherArray = (JSONArray) weatherCondition.get("weather");
                    String unos = "";
                    for (Object pom : weatherArray) {
                        unos += "Description: " + ((JSONObject) pom).get("description") + "\n";
                    }
                    JSONObject temp = (JSONObject) weatherCondition.get("main");
                    double temperature = (Double) temp.get("temp") - 273.15;

                    unos += "Temperature: " + Math.round(temperature) + " Cellsius" + "\n";
                    unos += "Pressure: "+temp.get("pressure")+"\n";
                    unos += "Humidity: " + temp.get("humidity") + "%" + "\n";



                    JSONObject windSpeed = (JSONObject) weatherCondition.get("wind");
                    unos += "Wind speed: " + windSpeed.get("speed") + " KHP" + "\n";
                    poljePrikaza.setText(unos);
                    bf.close();

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}