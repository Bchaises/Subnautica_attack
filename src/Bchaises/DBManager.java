package Bchaises;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private String URL = "jdbc:mysql://127.0.0.1/subnautica_attack?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String LOGIN = "Benjamin";
    private String PASSWORD = "x1XnnrQfLeqmvhCT";
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

    public ArrayList<Poisson> getAllPoisson() throws SQLException{
        ArrayList<Poisson> res = new ArrayList<>();
        try{
            this.stmt = this.connection.createStatement();
            String query = "SELECT * FROM poisson";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                int id = rs.getInt("id_p");
                String nom = rs.getString("nom_p");
                Poisson p = new Poisson(id, nom);
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

    public String getPoissonUrlByName(String name)throws SQLException {
        String url;
        String URLimage = null;
        try {
            this.stmt = this.connection.createStatement();
            String query = "SELECT image_p FROM poisson WHERE nom_p = \'" + name + "\'";
            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            URLimage = rs.getString("image_p");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                this.stmt.close();
            }
        }

        return URLimage;
    }

    public ArrayList<Explorateur> getAllExplorateur() throws SQLException{
        ArrayList<Explorateur> res = new ArrayList<>();
        try{
            this.stmt = this.connection.createStatement();
            String query = "SELECT * FROM explorateur";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                int id = rs.getInt("id_e");
                String nom = rs.getString("nom_e");
                Explorateur explo = new Explorateur(id, nom);
                res.add(explo);
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

    public Poisson getPoissonById(int id) throws SQLException{
        Poisson p = new Poisson();
        try{
            this.stmt = this.connection.createStatement();
            String query = "SELECT * FROM poisson WHERE id_p = "+ id;
            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            String nom = rs.getString("nom_p");
            p = new Poisson(id, nom);
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
            String query = "SELECT * FROM explorateur WHERE nom_e = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,n);

            ResultSet rs = preparedStatement.executeQuery();

            rs.next();
            int id = rs.getInt("id_e");
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

    public ArrayList<Poisson> getPoissonCapture(Explorateur explo) throws SQLException{
        ArrayList<Poisson> res = new ArrayList<>();
        try {
            this.stmt = this.connection.createStatement();
            String query = "SELECT Poisson.id_p, Poisson.nom_p, lvl_p " +
                           "FROM Explorateur, Poisson, capture " +
                           "WHERE Poisson.id_p = capture.id_p " +
                           "AND capture.id_e = explorateur.id_e " +
                           "AND Explorateur.id_e = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,explo.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id_p");
                String nom = rs.getString("nom_p");
                int niveau = rs.getInt("lvl_p");

                Poisson p = new Poisson(id, nom, 100,niveau);
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

    public String getExplorateurUrlByName(String name)throws SQLException {
        String url;
        String URLimage = null;
        try {
            this.stmt = this.connection.createStatement();
            String query = "SELECT image_e FROM explorateur WHERE nom_e = \'" + name + "\'";
            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            URLimage = rs.getString("image_e");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                this.stmt.close();
            }
        }

        return URLimage;
    }
}
