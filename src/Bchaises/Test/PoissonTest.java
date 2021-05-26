package Bchaises.Test;

import Bchaises.Poisson;

import static org.junit.jupiter.api.Assertions.*;

class PoissonTest {

    @org.junit.jupiter.api.Test
    void getId() {
        Poisson p = new Poisson();
        assertEquals(0,p.getId());
    }

    @org.junit.jupiter.api.Test
    void getNom() {
        Poisson p = new Poisson();
        assertEquals("Unknown",p.getNom());
    }

    @org.junit.jupiter.api.Test
    void getPointDeVie() {
        Poisson p = new Poisson();
        assertEquals(-1,p.getPointDeVie());
    }

    @org.junit.jupiter.api.Test
    void getNiveau() {
        Poisson p = new Poisson();
        assertEquals(-1,p.getNiveau());
    }

    @org.junit.jupiter.api.Test
    void getAttaque() {
    }

    @org.junit.jupiter.api.Test
    void setId() {
    }

    @org.junit.jupiter.api.Test
    void setNom() {
    }

    @org.junit.jupiter.api.Test
    void setPointDeVie() {
    }

    @org.junit.jupiter.api.Test
    void setNiveau() {
    }

    @org.junit.jupiter.api.Test
    void setAttaque() {
    }

    @org.junit.jupiter.api.Test
    void testToString() {
    }

    @org.junit.jupiter.api.Test
    void crier() {
    }

    @org.junit.jupiter.api.Test
    void addAttaque() {
    }

    @org.junit.jupiter.api.Test
    void attaqueSur() {
    }
}