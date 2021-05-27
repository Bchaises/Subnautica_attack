package Bchaises;

import Bchaises.showPoisson.interfacePoisson;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
	    /*DBManager bdd = new DBManager();
	    bdd.connection();

        ArrayList<Poisson> liste = new ArrayList<>();
        liste = bdd.getAllPoisson();

        for (int i = 0; i < 5 ; i++){
            System.out.println(liste.get(i).getId() + " : " + liste.get(i).getNom());
        }

        Poisson warper = bdd.getPoissonById(1);
        System.out.println(warper.getId() + " : " + warper.getNom());

        System.out.println("Explorateur quel est ton nom ?");
        Scanner sc = new Scanner(System.in);
        String n = sc.nextLine();

        Explorateur e1 = bdd.getExplorateurByName(n);

        System.out.println("Voici tes poissons " + n);
        ArrayList<Poisson> tab = bdd.getPoissonCapture(e1);
        for (Poisson p : tab){
            System.out.println(p.getNom() + " lvl " + p.getNiveau());
        }*/


        interfacePoisson window = new interfacePoisson();
        Color myColor = Color.decode("#292D3E");
        window.getContentPane().setBackground(myColor);
        window.setVisible(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(window);
            //force chaque composant de la fenêtre à appeler sa méthode updateUI
        } catch (InstantiationException e) {
        } catch (ClassNotFoundException e) {
        } catch (UnsupportedLookAndFeelException e) {
        } catch (IllegalAccessException e) {}

    }
}
