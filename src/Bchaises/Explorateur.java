package Bchaises;

public class Explorateur {

    //attributs
    private int id;
    private String nom;

    // constructeur
    public Explorateur(){
        this.id = 0;
        this.nom = "Unknown";
    }

    //constructeur paramétré
    public Explorateur(int i, String n){
        this.id = i;
        this.nom = n;
    }

    // accesseurs
    //getter

    public int getId(){ return this.id;}
    public String getNom(){ return this.nom; }

    //setter
    public void setId(int i) { this.id = i; }
    public void setNom(String n) {this.nom = n; }

    @Override
    public String toString(){
        String res = nom + " : [id="+ id+"]\n";
        return res;
    }
}
