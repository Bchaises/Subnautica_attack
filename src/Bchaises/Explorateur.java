package Bchaises;

public class Explorateur {

    //attributs
    private int id;
    private String nom;
    private String image;

    // constructeur
    public Explorateur(){
        this.id = 0;
        this.nom = "Unknown";
        this.image = "Unknown";
    }

    //constructeur paramétré
    public Explorateur(int i, String n){
        this.id = i;
        this.nom = n;
    }

    public Explorateur(int i, String n, String image){
        this.id = i;
        this.nom = n;
        this.image = image;
    }

    // accesseurs
    //getter

    public int getId(){ return this.id;}
    public String getNom(){ return this.nom; }
    public String getImage(){ return this.image; }

    //setter
    public void setId(int i) { this.id = i; }
    public void setNom(String n) {this.nom = n; }
    public void setImage(String image) { this.image = image; }

    @Override
    public String toString(){
        String res = nom + " : [id="+ id+"]\n";
        return res;
    }
}
