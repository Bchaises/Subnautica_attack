package Bchaises.Test;

import Bchaises.DBManager;
import Bchaises.Explorateur;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DBManagerTest {

    @Test
    void DBManager() {
    }

    @Test
    void connection() {
        DBManager bdd = new DBManager();
        boolean res = bdd.connection();
        assertTrue(bdd.connection(),"Connection échoué");
    }

    @Test
    void getAllPoissons() {
    }

    @Test
    void getPoissonsById() {
    }

    @Test
    void getExplorateurByName() {
    }

    @Test
    void getPoissonsCapture() {
    }

    @Test
    void getAllPoisson() {
    }

    @Test
    void getAllExplorateur() throws SQLException {
        DBManager bdd = new DBManager();
        bdd.connection();
        ArrayList<Explorateur> ListeBddExplo = new ArrayList<Explorateur>();
        ListeBddExplo = bdd.getAllExplorateur();

        ArrayList<Explorateur> ListeExplo = new ArrayList<Explorateur>();
        Explorateur e1 = new Explorateur(1,"Robin Goodall");
        Explorateur e2 = new Explorateur(2,"Ryley Robinson");

        ListeExplo.add(e1);
        ListeExplo.add(e2);

        assertEquals(ListeExplo,ListeBddExplo);
    }

    @Test
    void getPoissonById() {
    }

    @Test
    void testGetExplorateurByName() {
    }

    @Test
    void getPoissonCapture() {
    }
}