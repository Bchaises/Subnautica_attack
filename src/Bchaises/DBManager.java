package Bchaises;

import com.mysql.cj.protocol.Resultset;

import javax.xml.transform.Result;
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

    public ArrayList<Explorateur> getAllExplorateurFull() throws SQLException{
        ArrayList<Explorateur> res = new ArrayList<>();
        try{
            this.stmt = this.connection.createStatement();
            String query = "SELECT explorateur.id_e,explorateur.nom_e, explorateur.image_e FROM explorateur, capture WHERE explorateur.id_e = capture.id_e GROUP BY capture.id_e";
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

    public Poisson getPoissonByName(String nom) throws SQLException{
        Poisson p = new Poisson();
        try{
            this.stmt = this.connection.createStatement();
            String query = "SELECT * FROM poisson WHERE nom_p = \'"+ nom + "\'";
            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            int id = rs.getInt("id_p");
            int pointDeVie = rs.getInt("pv_p");
            int niveau = rs.getInt("lvl_p");
            String image = rs.getString("image_p");
            p = new Poisson(id, nom,pointDeVie, niveau,image);
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
            String image = rs.getString("image_e");
            explo = new Explorateur(id, n,image);
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

    public void addExplorateur(String nom, String url) throws SQLException{
        try{
            this.stmt = this.connection.createStatement();
            String query = "INSERT INTO explorateur VALUES(NULL,\'" + nom + "\', \'" + url + "\')";
            stmt.executeUpdate(query);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (stmt != null){
                this.stmt.close();
            }
        }
    }

    public void addPoisson(String nom, String url) throws SQLException{
        try{
            this.stmt = this.connection.createStatement();
            String query = "INSERT INTO poisson VALUES(NULL,\'" + nom + "\',100,1 ,\'" + url + "\')";
            stmt.executeUpdate(query);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (stmt != null){
                this.stmt.close();
            }
        }
    }

    public int getIdPoissonByName(String nom) throws SQLException {
        int id = 0;
        try{
            this.stmt = this.connection.createStatement();
            String query = "SELECT id_p FROM poisson WHERE nom_p = \'" + nom + "\'";
            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            id = rs.getInt("id_p");

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (stmt != null){
                this.stmt.close();
            }
        }

        return id;
    }

    public int getIdExplorateurByName(String nom) throws SQLException {
        int id = 0;
        try{
            this.stmt = this.connection.createStatement();
            String query = "SELECT id_e FROM explorateur WHERE nom_e = \'" + nom + "\'";
            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            id = rs.getInt("id_e");

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (stmt != null){
                this.stmt.close();
            }
        }

        return id;
    }

    public void addCapturePoisson(int id_p, int id_e) throws SQLException {
        try{
            this.stmt = this.connection.createStatement();
            String query = "INSERT INTO capture VALUES(\'" + id_e + "\',\'" + id_p + "\')";
            stmt.executeUpdate(query);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (stmt != null){
                this.stmt.close();
            }
        }
    }
}
