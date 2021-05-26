package Bchaises;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {

    private String URL = "jdbc:mysql://127.0.0.1/subnautica_attack?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String LOGIN = "Benjamin";
    private String PASSWORD = "?MiD6S4Q6AFAgk$";
    private Connection connection;
    private Statement stmt;

    public void DBManager() { this.connection(); }

    public boolean connection(){
        System.out.println("Connection au systeme...");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println("Où est MysQL JDBC DRIVER?");
            e.printStackTrace();
            return false;
        }

        this.connection = null;
        try{
            this.connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        }catch (SQLException e){
            System.out.println("Erreur de connection!");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public ArrayList<Poissons> getAllPoissons() throws SQLException{
        ArrayList<Poissons> res = new ArrayList<>();
        try{
            this.stmt = this.connection.createStatement();
            String query = "SELECT * FROM poissons";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                int id = rs.getInt("id_poisson");
                String nom = rs.getString("nom_poisson");
                Poissons p = new Poissons(id, nom);
                res.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if (stmt != null){
                this.stmt.close();
            }
        }

        return res;
    }

    public Poissons getPoissonsById(int id) throws SQLException{
        Poissons p = new Poissons();
        try{
            this.stmt = this.connection.createStatement();
            String query = "SELECT * FROM poissons WHERE id_poisson = "+ id;
            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            String nom = rs.getString("nom_poisson");
            p = new Poissons(id, nom);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (stmt != null){
                this.stmt.close();
            }
        }

        return p;
    }

    public Explorateur getExplorateurByName(String n) throws SQLException{
        Explorateur explo = new Explorateur();
        try {
            this.stmt = this.connection.createStatement();
            String query = "SELECT * FROM explorateur WHERE nom_explo = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,n);

            ResultSet rs = preparedStatement.executeQuery();

            rs.next();
            int id = rs.getInt("id_explo");
            explo = new Explorateur(id, n);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if (stmt != null){
                this.stmt.close();
            }
        }

        return explo;
    }

    public ArrayList<Poissons> getPoissonsCapture(Explorateur explo) throws SQLException{
        ArrayList<Poissons> res = new ArrayList<>();
        try {
            this.stmt = this.connection.createStatement();
            String query = "SELECT Poisson.id_poisson, Poisson.nom_poisson, lvl_poisson " +
                           "FROM Explorateur, Poisson, capturePoisson " +
                           "WHERE Poisson.id_poissons = capturePoisson.id_poisson " +
                           "AND capturePoisson.id_explo = explorateur.id_explo " +
                           "AND Explorateur.id_explo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,explo.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id_poisson");
                String nom = rs.getString("nom_poisson");
                int niveau = rs.getInt("lvl_poisson");

                Poissons p = new Poissons(id, nom, 100,niveau);
                res.add(p);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            if (stmt != null){
                this.stmt.close();
            }
        }

        return res;
    }
}
