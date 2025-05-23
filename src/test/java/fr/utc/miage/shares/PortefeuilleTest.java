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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PortefeuilleTest {

    private static final String PORTFOLIO_VALUE = "Valeur du portefeuille : 500.0 €";
    private static final double INITIAL_VALUE = 500.0;
    private static final double ADD_VALUE = 100.0;
    private static final double ADD_VALUE_NEGATIF = -100.0;
    private static final ActionSimple ACTION_TEST_SIMPLE = new ActionSimple("ActionTest");
    private static final ActionSimple ACTION_TEST_2_SIMPLE = new ActionSimple("ActionTest2");
    private static final ActionSimple ACTION_TEST_3_SIMPLE = new ActionSimple("ActionTest3");
    private static final int ACTION_NB = 10;
    private static final float ACTION_VALUE = 10.0f;
    private static final float ACTION_VALUE_2 = 20.0f;
    private static final Jour ACTION_JOUR = new Jour(2023, 101);
    private static final List<ActionSimple> ACTION_LIST = List.of(ACTION_TEST_SIMPLE, ACTION_TEST_2_SIMPLE,
            ACTION_TEST_3_SIMPLE);
    private static final List<Float> FRACTION_LIST = List.of(0.5f, 0.3f, 0.2f);
    private static final ActionComposee ACTION_COMPOSEE = new ActionComposee("ActionComposee", ACTION_LIST,
            FRACTION_LIST);

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
    void testAjouterDesFondsNegatif() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> portefeuille.ajouterDesFonds(ADD_VALUE_NEGATIF));
    }

    @Test
    void testRetirerDesFondsPositif() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        portefeuille.retirerDesFonds(ADD_VALUE);
        assertEquals(portefeuille.getValue(), INITIAL_VALUE - ADD_VALUE);
    }

    @Test
    void testRetirerDesFondsNegatif() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> portefeuille.retirerDesFonds(ADD_VALUE_NEGATIF));
    }

    @Test
    void testRetirerDesFondsTropGrand() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> portefeuille.retirerDesFonds(INITIAL_VALUE + ADD_VALUE));
    }

    @Test
    void testAcheterUneActionSimple() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        ACTION_TEST_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE);
        portefeuille.acheterAction(ACTION_TEST_SIMPLE, ACTION_NB, ACTION_JOUR);
        assertEquals(ACTION_NB, portefeuille.getListeAction().get(ACTION_TEST_SIMPLE));
        double finalValue = INITIAL_VALUE - (ACTION_NB * ACTION_VALUE);
        assertEquals(finalValue, portefeuille.getValue());
    }

    @Test
    void testAcheterMemeActionSimple() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        ACTION_TEST_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE);
        portefeuille.acheterAction(ACTION_TEST_SIMPLE, ACTION_NB, ACTION_JOUR);
        portefeuille.acheterAction(ACTION_TEST_SIMPLE, ACTION_NB, ACTION_JOUR);
        assertEquals(2 * ACTION_NB, portefeuille.getListeAction().get(ACTION_TEST_SIMPLE));
        double finalValue = INITIAL_VALUE - (2 * ACTION_NB * ACTION_VALUE);
        assertEquals(finalValue, portefeuille.getValue());
    }

    @Test
    void testAcheterPlusieursActionsDiff() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        ACTION_TEST_2_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE);
        ACTION_TEST_3_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE);
        portefeuille.acheterAction(ACTION_TEST_2_SIMPLE, ACTION_NB, ACTION_JOUR);
        portefeuille.acheterAction(ACTION_TEST_3_SIMPLE, ACTION_NB, ACTION_JOUR);
        assertEquals(ACTION_NB, portefeuille.getListeAction().get(ACTION_TEST_2_SIMPLE));
        assertEquals(ACTION_NB, portefeuille.getListeAction().get(ACTION_TEST_3_SIMPLE));
        double finalValue = INITIAL_VALUE - (ACTION_NB * 2 * ACTION_VALUE);
        assertEquals(finalValue, portefeuille.getValue());
    }

    @Test
    void testAcheterUneActionComposee() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        ACTION_TEST_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE);
        ACTION_TEST_2_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE_2);
        ACTION_TEST_3_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE);
        portefeuille.acheterAction(ACTION_COMPOSEE, ACTION_NB, ACTION_JOUR);
        assertEquals(ACTION_NB, portefeuille.getListeAction().get(ACTION_COMPOSEE));
        double finalValue = INITIAL_VALUE - (ACTION_NB * (ACTION_VALUE * FRACTION_LIST.get(0)
                + ACTION_VALUE_2 * FRACTION_LIST.get(1) + ACTION_VALUE * FRACTION_LIST.get(2)));
        assertEquals(finalValue, portefeuille.getValue());
    }

    @Test
    void testAcheterMemeActionComposee() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        ACTION_TEST_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE);
        ACTION_TEST_2_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE_2);
        ACTION_TEST_3_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE);
        portefeuille.acheterAction(ACTION_COMPOSEE, ACTION_NB, ACTION_JOUR);
        portefeuille.acheterAction(ACTION_COMPOSEE, ACTION_NB, ACTION_JOUR);
        assertEquals(2 * ACTION_NB, portefeuille.getListeAction().get(ACTION_COMPOSEE));
        double finalValue = INITIAL_VALUE - (2 * ACTION_NB * (ACTION_VALUE * FRACTION_LIST.get(0)
                + ACTION_VALUE_2 * FRACTION_LIST.get(1) + ACTION_VALUE * FRACTION_LIST.get(2)));
        assertEquals(finalValue, portefeuille.getValue());
    }

    @Test
    void testAcheterPlusieursActionsDiffComposees() {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setValue(INITIAL_VALUE);
        ACTION_TEST_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE);
        ACTION_TEST_2_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE_2);
        ACTION_TEST_3_SIMPLE.enrgCours(ACTION_JOUR, ACTION_VALUE);
        portefeuille.acheterAction(ACTION_COMPOSEE, ACTION_NB, ACTION_JOUR);
        portefeuille.acheterAction(ACTION_TEST_2_SIMPLE, ACTION_NB, ACTION_JOUR);
        assertEquals(ACTION_NB, portefeuille.getListeAction().get(ACTION_COMPOSEE));
        assertEquals(ACTION_NB, portefeuille.getListeAction().get(ACTION_TEST_2_SIMPLE));
        double finalValue = INITIAL_VALUE - (ACTION_NB * (ACTION_VALUE * FRACTION_LIST.get(0)
                + ACTION_VALUE_2 * FRACTION_LIST.get(1) + ACTION_VALUE * FRACTION_LIST.get(2)));
        assertEquals(finalValue, portefeuille.getValue());
    }

}