package Bchaises.showPoisson;

import Bchaises.Attaque;
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

    private JButton combatButton;
    private JComboBox selectExplorateur1;
    private JComboBox selectPoisson1;
    private JComboBox selectExplorateur2;
    private JComboBox selectPoisson2;
    private JPanel PanelInterfacePoisson;
    private JLabel poisson1;
    private JLabel poisson2;
    private JLabel explorateur1;
    private JLabel explorateur2;
    private JPanel panelExplorateur1;
    private JPanel panelExplorateur2;
    private JLabel pointDeVie1;
    private JLabel niveau1;
    private JLabel pointDeVie2;
    private JLabel niveau2;
    private JButton retourAccueilButton;
    private JComboBox comboBoxAttaque1;
    private JComboBox comboBoxAttaque2;
    private JLabel JLabelMessageDéfaite;
    private JLabel JLabelMessageVictoire;

    public interfacePoisson()  {

        add(PanelInterfacePoisson);
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
                pointDeVie1.setText("");
                niveau1.setText("");
                System.out.println(selectExplorateur1.getSelectedItem());
                FillcomboPoisson(selectPoisson1, (String) selectExplorateur1.getSelectedItem());
                showExplorateur(explorateur1,(String) selectExplorateur1.getSelectedItem());
            }
        });

        selectExplorateur2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPoisson2.removeAllItems();
                pointDeVie2.setText("");
                niveau2.setText("");
                System.out.println(selectExplorateur2.getSelectedItem());
                FillcomboPoisson(selectPoisson2,(String) selectExplorateur2.getSelectedItem());
                showExplorateur(explorateur2,(String) selectExplorateur2.getSelectedItem());

            }
        });

        selectPoisson1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxAttaque1.removeAllItems();
                System.out.println(selectPoisson1.getSelectedItem());
                showPoisson(poisson1,(String) selectPoisson1.getSelectedItem());
                showPointDeVie(pointDeVie1,(String) selectPoisson1.getSelectedItem());
                showNiveau(niveau1,(String) selectPoisson1.getSelectedItem());
                try {
                    FillcomboAttaque(comboBoxAttaque1,(String) selectExplorateur1.getSelectedItem(),(String) selectPoisson1.getSelectedItem());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        selectPoisson2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxAttaque2.removeAllItems();
                System.out.println(selectPoisson2.getSelectedItem());
                showPoisson(poisson2,(String) selectPoisson2.getSelectedItem());
                showPointDeVie(pointDeVie2,(String) selectPoisson2.getSelectedItem());
                showNiveau(niveau2,(String) selectPoisson2.getSelectedItem());
                try {
                    FillcomboAttaque(comboBoxAttaque2,(String) selectExplorateur2.getSelectedItem(),(String) selectPoisson2.getSelectedItem());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        combatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DBManager bdd = new DBManager();
                bdd.connection();


                boolean fin = false;
                while (!fin){


                    Poisson p1 = new Poisson();
                    Poisson p2 = new Poisson();
                    Attaque a1 = new Attaque();
                    Attaque a2 = new Attaque();
                    Explorateur e1 = new Explorateur();
                    Explorateur e2 = new Explorateur();

                    try {
                        p1 = bdd.getPoissonByName( selectPoisson1.getSelectedItem().toString() );
                        p2 = bdd.getPoissonByName( selectPoisson2.getSelectedItem().toString() ) ;

                        a1 = bdd.getAttaqueByName( comboBoxAttaque1.getSelectedItem().toString());
                        a2 = bdd.getAttaqueByName( comboBoxAttaque2.getSelectedItem().toString());

                        e1 = bdd.getExplorateurByName( selectExplorateur1.getSelectedItem().toString());
                        e2 = bdd.getExplorateurByName( selectExplorateur2.getSelectedItem().toString());

                        p1.addAttaque(a1);
                        p2.addAttaque(a2);

                        p1.attaqueSur(p2,0);
                        bdd.setPointDeVie(p2.getPointDeVie(), p2.getId());

                        if (p2.getPointDeVie() <= 0){
                            JLabelMessageDéfaite.setText(e2.getNom() + " votre poisson " + p2.getNom() + " est mort! Il sera remis à zéro.");
                            JLabelMessageDéfaite.setForeground(new Color(255,0,0));

                            JLabelMessageVictoire.setText(e1.getNom() + " votre poisson " + p1.getNom() + " a gagné! Il gagne 1 niveau et 100 de vie!");
                            JLabelMessageVictoire.setForeground(new Color(0,127,0));

                            fin = true;
                        }
                        p2.attaqueSur(p1,0);
                        bdd.setPointDeVie(p1.getPointDeVie(), p1.getId());

                        if(p1.getPointDeVie() <= 0){
                            JLabelMessageDéfaite.setText(e1.getNom() + " votre poisson " + p1.getNom() + " est mort! Il sera remis à zéro.");
                            JLabelMessageDéfaite.setForeground(new Color(255,0,0));

                            JLabelMessageVictoire.setText(e2.getNom() + " votre poisson " + p2.getNom() + " a gagné! Il gagne 1 niveau et 100 de vie!");
                            JLabelMessageVictoire.setForeground(new Color(0,127,0));

                            fin = true;
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

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

    private void FillcomboAttaque(JComboBox combobox,String explorateur, String Poisson) throws SQLException {
        DBManager bdd = new DBManager();
        bdd.connection();

        ArrayList<String> a = new ArrayList<String>();

        Explorateur explo = new Explorateur();
        explo = bdd.getExplorateurByName(explorateur);

        Poisson p = new Poisson();
        p = bdd.getPoissonByName(Poisson);

        try {
            a = bdd.getAttaqueWithName(explorateur, Poisson);
            for (int ii = 0 ; ii < a.size(); ii++){
                combobox.addItem(a.get(ii));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showPointDeVie(JLabel pointDeVieLabel, String nom){
        DBManager bdd = new DBManager();
        bdd.connection();
        Poisson p = new Poisson();
        String pointDeVie = null;

        try {
            p = bdd.getPoissonByName(nom);
            pointDeVie = Integer.toString(p.getPointDeVie());
            pointDeVieLabel.setText("Points de vie : " + pointDeVie);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void showNiveau(JLabel niveauLabel, String nom){
        DBManager bdd = new DBManager();
        bdd.connection();
        Poisson p = new Poisson();
        String niveau = null;

        try {
            p = bdd.getPoissonByName(nom);
            niveau = Integer.toString(p.getNiveau());
            niveauLabel.setText("Niveau : " + niveau);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
