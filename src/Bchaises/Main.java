package Bchaises;

import javax.swing.*;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
	    DBManager bdd = new DBManager();
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
        }

        // Déclaration de la fenêtre
        JFrame fenetre = new JFrame();

        // Configuration de la fenêtre
        fenetre.setTitle("Ma première fenêtre Java");
        fenetre.setSize(400,100);
        fenetre.setLocationRelativeTo(null);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // affichage de la fenêtre
        fenetre.setVisible(true);

    }
}
