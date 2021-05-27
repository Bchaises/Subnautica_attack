package Bchaises.showPoisson;

import Bchaises.DBManager;
import Bchaises.Explorateur;
import Bchaises.Poisson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class interfacePoisson extends JFrame {

    private JButton attaquerButton;
    private JButton attaquerButton1;
    private JComboBox selectExplorateur1;
    private JComboBox selectPoisson1;
    private JComboBox selectExplorateur2;
    private JComboBox selectPoisson2;
    private JPanel PanelDepart;
    private JButton button1;
    private JButton button3;
    private JLabel poisson1;
    private JLabel poisson2;
    private JLabel explorateur1;
    private JLabel explorateur2;
    private JPanel panelExplorateur1;
    private JPanel panelExplorateur2;

    public interfacePoisson()  {

        add(PanelDepart);
        setTitle("BIENVENUE!!");
        setSize(800,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FillcomboExplo(selectExplorateur1);
        FillcomboExplo(selectExplorateur2);

        selectExplorateur1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPoisson1.removeAllItems();
                System.out.println(selectExplorateur1.getSelectedItem());
                FillcomboPoisson(selectPoisson1, (String) selectExplorateur1.getSelectedItem());
                showExplorateur(explorateur1,(String) selectExplorateur1.getSelectedItem());
            }
        });

        selectExplorateur2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPoisson2.removeAllItems();
                System.out.println(selectExplorateur2.getSelectedItem());
                FillcomboPoisson(selectPoisson2,(String) selectExplorateur2.getSelectedItem());
                showExplorateur(explorateur2,(String) selectExplorateur2.getSelectedItem());
            }
        });

        selectPoisson1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(selectPoisson1.getSelectedItem());
                showPoisson(poisson1,(String) selectPoisson1.getSelectedItem());
            }
        });

        selectPoisson2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(selectPoisson2.getSelectedItem());
                showPoisson(poisson2,(String) selectPoisson2.getSelectedItem());
            }
        });

        attaquerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("attaque !!!");
            }
        });
    }

    private void FillcomboExplo(JComboBox combobox){
        DBManager bdd = new DBManager();
        bdd.connection();
        try {
            ArrayList<Explorateur> e1 = new ArrayList<Explorateur>();
            e1 = bdd.getAllExplorateur();
            for (int ii = 0 ; ii < e1.size(); ii++){
                combobox.addItem(e1.get(ii).getNom());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void FillcomboPoisson(JComboBox combobox,String explorateur){
        DBManager bdd = new DBManager();
        bdd.connection();
        try {
            ArrayList<Poisson> p1 = new ArrayList<Poisson>();
            p1 = bdd.getPoissonCapture(bdd.getExplorateurByName(explorateur));
            for (int ii = 0 ; ii < p1.size(); ii++){
                combobox.addItem(p1.get(ii).getNom());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showPoisson(JLabel poisson, String nom){
        DBManager bdd = new DBManager();
        bdd.connection();
        String URLimage = null;

        try {
            URLimage = bdd.getPoissonUrlByName(nom);
            poisson.setIcon(new ImageIcon(URLimage));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void showExplorateur(JLabel explorateur, String nom){
        DBManager bdd = new DBManager();
        bdd.connection();
        String URLexplo = null;

        try {
            URLexplo = bdd.getExplorateurUrlByName(nom);
            explorateur.setIcon(new ImageIcon(URLexplo));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
