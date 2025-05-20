package fr.utc.miage.shares;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PortefeuilleTest {

    @Test
    void testVisuPortefeuille() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(500.00);
        assertEquals("Valeur du portefeuille : 500.0 €", portefeuille.VisuPortefeuille());
    }

    @Test
    void testGetValue() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(1000.00);
        assertEquals(1000.00, portefeuille.getValue());
    }
}
