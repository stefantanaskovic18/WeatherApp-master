package dogadjaji;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileReader;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;


public class KlikIzborDrzave implements ItemListener {

    private JComboBox poljeGrad;
    private JComboBox poljeDrzava;

    public KlikIzborDrzave(JComboBox poljeGrad, JComboBox poljeDrzava) {
        this.poljeDrzava = poljeDrzava;
        this.poljeGrad = poljeGrad;
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        String izabranaDrzava = poljeDrzava.getSelectedItem().toString();
        poljeGrad.removeAllItems();
        try {
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray)parser.parse(new FileReader("src\\cities.json"));
            for(Object o : array) {
                JSONObject drzava = (JSONObject)o;
                if(drzava.get("country").equals(izabranaDrzava)) {
                    poljeGrad.addItem(drzava.get("name"));
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        poljeGrad.setVisible(true);
    }
}