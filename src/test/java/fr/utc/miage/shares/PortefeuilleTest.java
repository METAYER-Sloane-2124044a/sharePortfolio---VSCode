/*
 * Copyright 2025 David Navarre &lt;David.Navarre at irit.fr&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.utc.miage.shares;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PortefeuilleTest {

    private static final String PORTFOLIO_VALUE = "Valeur du portefeuille : 500.0 €";
    private static final double INITIAL_VALUE = 500.0;
    private static final double ADD_VALUE = 100.0;
    private static final double ADD_VALUE_NEGATIF = -100.0;
    private static final float DEFAULT_ACTION_VALUE = 100.0F;
    private static final String DEFAULT_ACTION_NAME = "Action Simple Test";
    private static final Jour A_DAY = new Jour(2025, 100);
    private static final ActionSimple A_SIMPLE_ACTION = new ActionSimple(DEFAULT_ACTION_NAME);

    @Test
    void testVisuPortefeuille() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        assertEquals(PORTFOLIO_VALUE, portefeuille.visuPortefeuille());
    }

    @Test
    void testAjouterDesFondsPositif() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        portefeuille.ajouterDesFonds(ADD_VALUE);
        assertEquals(portefeuille.getValue(), INITIAL_VALUE + ADD_VALUE);
    }

    @Test
    void testGetValueActionsShouldWork() {
        Portefeuille portefeuille = new Portefeuille();
        assertDoesNotThrow(portefeuille::getValueActions);
    }

    @Test
    void testAjouterDesFondsNegatif() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> portefeuille.ajouterDesFonds(ADD_VALUE_NEGATIF));
    }

    @Test
    void testVendreAction_actionPasPresentDansPortefeuille_shouldRaiseException() {
        Portefeuille portefeuille = new Portefeuille();
        assertThrows(IllegalArgumentException.class, () -> portefeuille.vendreActions(A_SIMPLE_ACTION, 1));
    }

    @Test
    void testVendreAction_vendrePlusDActionsQueCeQuIlYADansLePortefeuille_shouldRaiseException() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.getListeActions().put(A_SIMPLE_ACTION, 1);
        assertThrows(IllegalArgumentException.class, () -> portefeuille.vendreActions(A_SIMPLE_ACTION, 2));
    }
}
