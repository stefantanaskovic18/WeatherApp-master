package grafika;
import javax.swing.*;
import java.awt.*;
import java.io.FileReader;

import dogadjaji.KlikIzborDrzave;
import dogadjaji.KlikPrikaziPrognozu;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Prozor extends JFrame {

    private JLabel drzava;
    private JLabel grad;
    private JComboBox poljeDrzava;
    private JComboBox poljeGrad;
    private JButton dugmePrikaza;
    private JTextArea poljePrikazaPrognoze;
    private JScrollPane panel;

    public Prozor() {
        this.setLayout(null);
        this.setBounds(100, 100, 540, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initArea();
        initLabels();
        initComboBoxes();
        initButton();

        setVisible(true);
    }

    private void initArea() {
        poljePrikazaPrognoze = new JTextArea();
        poljePrikazaPrognoze.setLineWrap(true);

        panel = new JScrollPane();
        panel.setBounds(10, 150, 500, 400);
        panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        panel.getViewport().add(poljePrikazaPrognoze);
        add(panel);
    }

    private void initButton() {
        dugmePrikaza = new JButton("Show weather");
        dugmePrikaza.setBounds(10, 90, 200, 20);
        dugmePrikaza.addActionListener(new KlikPrikaziPrognozu(poljeGrad, poljePrikazaPrognoze));
        add(dugmePrikaza);
    }

    private void initComboBoxes() {
        poljeDrzava = new JComboBox();
        poljeDrzava.setBounds(200, 10, 250, 20);
        poljeDrzava.addItem("Check...");
        try {
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray)parser.parse(new FileReader("src\\countries.json"));
            for(Object o : array) {
                JSONObject drzava = (JSONObject)o;
                poljeDrzava.addItem((String)drzava.get("Name"));
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        add(poljeDrzava);

        poljeGrad = new JComboBox();
        poljeGrad.setBounds(200, 50, 250, 20);
        poljeGrad.setVisible(false);
        add(poljeGrad);

        poljeDrzava.addItemListener(new KlikIzborDrzave(poljeGrad, poljeDrzava));
    }

    private void initLabels() {
        drzava = new JLabel("Check country ");
        drzava.setFont(new Font("Times New Roman", 1, 17));
        drzava.setBounds(10, 10, 180, 20);
        add(drzava);

        grad = new JLabel("Check city ");
        grad.setFont(new Font("Times New Roman", 1, 17));
        grad.setBounds(10, 50, 180, 20);
        add(grad);


    }


}