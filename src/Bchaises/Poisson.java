package Bchaises;

import java.util.ArrayList;

public class Poisson {

    //attributs
    private int id;
    private String nom;
    private int pointDeVie;
    private int niveau;
    private String image;
    private ArrayList<Attaque> Attaque;

    //constructeur par défaut
    public Poisson(){
        this.id = 0;
        this.nom = "Unknown";
        this.pointDeVie = -1;
        this.niveau = -1;
        this.image = "Unknown";
        this.Attaque = new ArrayList<>();
    }

    //constructeur
    public Poisson(int i, String n, int p, int niv ){
        this.id = i;
        this.nom = n;
        this.pointDeVie = p;
        this.niveau = niv;
        this.Attaque = new ArrayList<>();
    }

    public Poisson(int i, String n, int p, int niv ,String image){
        this.id = i;
        this.nom = n;
        this.pointDeVie = p;
        this.niveau = niv;
        this.image = image;
        this.Attaque = new ArrayList<>();
    }

    public Poisson(int i, String n){
        this.id = i;
        this.nom = n;
        this.pointDeVie = 0;
        this.niveau = 0;
        this.Attaque = new ArrayList<>();
    }

    // getter
    public int getId(){ return this.id; }
    public String getNom(){ return this.nom; }
    public int getPointDeVie(){ return this.pointDeVie; }
    public int getNiveau(){ return this.niveau; }
    public String getImage(){ return this.image; }
    public ArrayList getAttaque(){ return this.Attaque; }

    //setter

    public void setId(int id) { this.id = id; }
    public void setNom(String n){ this.nom = n; }
    public void setPointDeVie(int p){ this.pointDeVie = p; }
    public void setNiveau(int niv){ this.niveau = niv; }
    public void setImage(String image) { this.image = image; }
    public void setAttaque(ArrayList Attaque){ this.Attaque = Attaque; }

    @Override
    public String toString(){
        String res = nom + " : [id="+id+"| nom="+nom+"| niveau=" + niveau + "|points de vie=" + pointDeVie +"]\n";
        for (Attaque at : this.Attaque){
            res = res + at.getNom()+"\n";
        }

        return res;
    }

    public void crier(){

        System.out.println("je suis " + this.nom);
    }

    public void addAttaque(Attaque a) { Attaque.add(a); }

    public void attaqueSur(Poisson p, int a) { p.setPointDeVie(p.getPointDeVie() - this.Attaque.get(a).getDegats());}

}
