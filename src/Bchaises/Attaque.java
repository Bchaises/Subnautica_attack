package Bchaises;

public class Attaque {

    //attributs
    private int id;
    private String nom;
    private int degats;

    // constructeur par défaut
    public Attaque(){
        this.id = 0;
        this.nom = "Unknown";
        this.degats = -1;
    }

    //accesseurs

    //getter
    public int getId(){ return this.id; }
    public String getNom(){ return this.nom; }
    public int getDegats(){ return this.degats; }

    //setter
    public void setId(int i){ this.id = i; }
    public void setNom(String n) {this.nom = n; }
    public void setDegats(int d) {this.degats = d; }

    @Override
    public String toString(){
        String res = nom + " : [id=" + id + "| degats="+degats + "]\n";
        return res;
    }
}
