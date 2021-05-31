package Bchaises.showPoisson;

import Bchaises.Attaque;
import Bchaises.DBManager;
import Bchaises.Explorateur;
import Bchaises.Poisson;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class AjoutAttaque extends JFrame{
    private JPanel PanelAjoutAttaque;
    private JButton retourAccueilButton;
    private JTextField textFieldName;
    private JTextField textFieldDegats;
    private JButton ajouterAttaqueButton;
    private JComboBox comboBoxExplorateur;
    private JComboBox comboBoxPoisson;
    private JButton apprendreLAttaqueButton;
    private JComboBox comboBoxAttaque;
    private JTextArea attentionPlusLesDégatsTextArea;
    private JLabel JLabelDegats;
    private JLabel JLabelApprentissage;
    private JLabel JLabelMessageMort;
    private JLabel JLabelPointDeVie;

    public AjoutAttaque() {
        add(PanelAjoutAttaque);
        setTitle("BIENVENUE!");
        setSize(800,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FillcomboExplo(comboBoxExplorateur);
        apprendreLAttaqueButton.setEnabled(false);

        retourAccueilButton.addActionListener(new ActionListener() {
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

        ajouterAttaqueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBManager bdd = new DBManager();
                bdd.connection();

                String nom = textFieldName.getText();
                int degats = Integer.parseInt(textFieldDegats.getText());

                try {
                    bdd.addAttaque(nom,degats);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        comboBoxExplorateur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxPoisson.removeAllItems();
                comboBoxAttaque.removeAllItems();

                if (comboBoxExplorateur.getSelectedItem() != null){
                    FillcomboPoisson(comboBoxPoisson, (String) comboBoxExplorateur.getSelectedItem());
                    FillcomboAttaque(comboBoxAttaque);
                }

            }
        });

        apprendreLAttaqueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        comboBoxAttaque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBManager bdd = new DBManager();
                bdd.connection();

                Attaque a = new Attaque();

                try {
                    if (comboBoxAttaque.getSelectedItem() != null){
                        a = bdd.getAttaqueByName((String) comboBoxAttaque.getSelectedItem());
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                if (comboBoxAttaque.getSelectedItem() != null){
                    JLabelDegats.setText( Integer.toString(a.getDegats()));
                    apprendreLAttaqueButton.setEnabled(true);
                }else{
                    JLabelDegats.setText("-");
                    apprendreLAttaqueButton.setEnabled(false);
                }

            }
        });

        apprendreLAttaqueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBManager bdd = new DBManager();
                bdd.connection();

                JLabelMessageMort.setText("");

                int random = (int) (Math.random() * ((Integer.parseInt(JLabelDegats.getText()) * 10) + 1));
                boolean possede = false;
                Poisson p = new Poisson();
                Attaque a = new Attaque();
                Explorateur explo = new Explorateur();
                try {
                    p = bdd.getPoissonByName((String) comboBoxPoisson.getSelectedItem());
                    a = bdd.getAttaqueByName( (String) comboBoxAttaque.getSelectedItem());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                ArrayList<String> tab_n = new ArrayList<>();
                try {
                    tab_n = bdd.getAttaqueWithName((String) comboBoxExplorateur.getSelectedItem(), (String) comboBoxPoisson.getSelectedItem());

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                for (int ii = 0; ii < tab_n.size(); ii++) {

                    if (comboBoxAttaque.getSelectedItem().toString().equals(tab_n.get(ii))) {
                        possede = true;
                        break;
                    }
                }

                if ( !possede || tab_n.size() == 0){
                    if (random >= 0 && random <= Integer.parseInt(JLabelDegats.getText())){

                        JLabelApprentissage.setText("Apprentissage réussi!");
                        JLabelApprentissage.setForeground(new Color(0, 127, 0));

                        try {
                            explo = bdd.getExplorateurByName( (String) comboBoxExplorateur.getSelectedItem());
                            bdd.addPossedeAttaque(explo.getId(), p.getId(), a.getId() );

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }else{
                        JLabelApprentissage.setText("Apprentissage échoué!");
                        JLabelApprentissage.setForeground(new Color(255,0,0));
                        try {
                            p.setPointDeVie(p.getPointDeVie() - 5);
                            bdd.setPointDeVie(p.getPointDeVie(), p.getId());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        JLabelPointDeVie.setText( Integer.toString(p.getPointDeVie()));

                        if (p.getPointDeVie() <= 0){
                            JLabelMessageMort.setText(comboBoxExplorateur.getSelectedItem().toString() + " votre poisson " + p.getNom() + " est mort! Il sera remis à zéro.");
                            JLabelMessageMort.setForeground(new Color(255,0,0));

                            try {
                                bdd.resetPoisson(p.getNom());
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }

                            JLabelDegats.setText("-");
                            comboBoxAttaque.removeAllItems();
                            JLabelPointDeVie.setText("-");
                            comboBoxPoisson.removeAllItems();

                            comboBoxExplorateur.removeAllItems();
                            FillcomboExplo(comboBoxExplorateur);
                        }

                    }
                }else{
                    JLabelApprentissage.setText("Votre poisson possède déjà cette attaque.");
                    JLabelApprentissage.setForeground(new Color(255,0,0));
                }


            }
        });

        comboBoxPoisson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Poisson p = new Poisson();

                DBManager bdd = new DBManager();
                bdd.connection();

                try {
                    if (comboBoxPoisson.getSelectedItem() != null){
                        p = bdd.getPoissonByName( comboBoxPoisson.getSelectedItem().toString());
                        JLabelPointDeVie.setText( Integer.toString(p.getPointDeVie()));
                    }else{
                        JLabelPointDeVie.setText("-");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

    }

    private void FillcomboExplo(JComboBox combobox){
        DBManager bdd = new DBManager();
        bdd.connection();
        try {
            ArrayList<Explorateur> e1 = new ArrayList<Explorateur>();
            e1 = bdd.getAllExplorateurFull();
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

    private void FillcomboAttaque(JComboBox combobox){
        DBManager bdd = new DBManager();
        bdd.connection();
        try{
            ArrayList<Attaque> tab_a = new ArrayList<Attaque>();
            tab_a = bdd.getAllAttaque();
            for (int ii = 0; ii < tab_a.size(); ii++){
                combobox.addItem(tab_a.get(ii).getNom());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
