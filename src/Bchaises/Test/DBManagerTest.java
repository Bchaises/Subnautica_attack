package Bchaises.Test;

import Bchaises.DBManager;
import org.junit.jupiter.api.Test;

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
}