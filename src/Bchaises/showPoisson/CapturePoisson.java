package Bchaises.showPoisson;

import Bchaises.DBManager;
import Bchaises.Explorateur;
import Bchaises.Poisson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class CapturePoisson extends JFrame{
    private JPanel PanelCapturePoisson;
    private JButton retourÀLAccueilButton;
    private JButton capturerButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;

    public CapturePoisson(){
        add(PanelCapturePoisson);
        setTitle("BIENVENUE!");
        setSize(800,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FillcomboExplo(comboBox1);
        capturerButton.setEnabled(false);

        retourÀLAccueilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e3) {
                    e3.printStackTrace();
                }
                fenetreDepart window = new fenetreDepart();
                window.setVisible(true);
            }
        });

        capturerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBManager bdd = new DBManager();
                bdd.connection();

                String explorateur = (String) comboBox1.getSelectedItem();
                String poisson = (String) comboBox2.getSelectedItem();

                int id_explorateur = 0;
                int id_poisson = 0;

                try {
                    id_explorateur = bdd.getIdExplorateurByName(explorateur);
                    id_poisson = bdd.getIdPoissonByName(poisson);
                    bdd.addCapturePoisson(id_poisson,id_explorateur);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                comboBox2.removeAllItems();
                FillcomboPoisson(comboBox2);
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("coucou");
                comboBox2.removeAllItems();
                FillcomboPoisson(comboBox2);
            }
        });

        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                capturerButton.setEnabled(true);
            }
        });
    }

    private void FillcomboPoisson(JComboBox combobox){

        DBManager bdd = new DBManager();
        bdd.connection();
        try {
            ArrayList<Poisson> p1 = new ArrayList<Poisson>();
            p1 = bdd.getAllPoissonFull();
            for (int ii = 0 ; ii < p1.size(); ii++){
                System.out.println(p1.get(ii).getNom());
                combobox.addItem(p1.get(ii).getNom());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
}
