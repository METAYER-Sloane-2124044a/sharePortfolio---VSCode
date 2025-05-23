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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.Port;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PortefeuilleTest {

    private static final String PORTFOLIO_VALUE = "Valeur du portefeuille : 500.0 €";
    private static final double INITIAL_VALUE = 500.0;
    private static final double ADD_VALUE = 100.0;
    private static final double ADD_VALUE_NEGATIF = -100.0;
    private static final float DEFAULT_ACTION_VALUE = 100.0F;
    private static final String DEFAULT_ACTION_NAME = "Action Simple Test";
    private static final String DEFAULT_ACTION_NAME_2 = "Action Simple Test 2";
    private static final String DEFAULT_ACTION_NAME_3 = "Action Simple Test 3";
    private static final Jour A_DAY = new Jour(2025, 100);
    private static final ActionSimple A_SIMPLE_ACTION = new ActionSimple(DEFAULT_ACTION_NAME);
    private static final ActionSimple A_SIMPLE_ACTION_2 = new ActionSimple(DEFAULT_ACTION_NAME_2);
    private static final Action A_ACTION_COMPOSEE = new ActionComposee(DEFAULT_ACTION_NAME_3,
            List.of(A_SIMPLE_ACTION, A_SIMPLE_ACTION_2), List.of(0.5F, 0.5F));
    private static final int A_QUANTITY = 5;

    static {
        A_SIMPLE_ACTION.enrgCours(A_DAY, DEFAULT_ACTION_VALUE);
        A_SIMPLE_ACTION_2.enrgCours(A_DAY, DEFAULT_ACTION_VALUE);
    }
    private static final String ACTION_NAME_1 = "Action 1";
    private static final String ACTION_NAME_2 = "Action 2";
    private static final String ACTION_COMPOSEE_LIB = "Action composee 1";
    private static final int ANNEE_1 = 2025;
    private static final int ANNEE_2 = 2026;
    private static final int JOUR_1 = 56;
    private static final int JOUR_2 = 34;
    private static final float COURS_1 = 100F;
    private static final float COURS_2 = 280F;
    private static final int QUANTITE_1 = 5;
    private static final int QUANTITE_2 = 17;
    private static final float FRACTION_1 = 0.6F;
    private static final float FRACTION_2 = 0.4F;

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

    @Test
    void testVendreAction_vendre1ActionCasNormal_shouldWorks() {

        // Before
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.ajouterDesFonds(ADD_VALUE);
        portefeuille.getListeActions().put(A_ACTION_COMPOSEE, A_QUANTITY);

        // Actions
        portefeuille.vendreActions(A_ACTION_COMPOSEE, 1);

        // Assertions
        Map<Action, Integer> portefeuilleMapShouldBe = new HashMap<>();
        portefeuilleMapShouldBe.put(A_ACTION_COMPOSEE, A_QUANTITY - 1);
        assertAll(
                () -> assertEquals(ADD_VALUE + A_ACTION_COMPOSEE.currentValeur(), portefeuille.getValue()),
                () -> assertEquals(portefeuilleMapShouldBe, portefeuille.getListeActions()));
    }

    @Test
    void testVendreAction_vendreToutesLesActions_shouldWorks() {
        // Before
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.ajouterDesFonds(ADD_VALUE);
        portefeuille.getListeActions().put(A_SIMPLE_ACTION, A_QUANTITY);
        portefeuille.getListeActions().put(A_SIMPLE_ACTION_2, A_QUANTITY);

        // Action
        portefeuille.vendreActions(A_SIMPLE_ACTION, A_QUANTITY);

        // Assertions
        Map<Action, Integer> portefeuilleMapShouldBe = new HashMap<>();
        portefeuilleMapShouldBe.put(A_SIMPLE_ACTION_2, A_QUANTITY);
        assertAll(
                () -> assertEquals(ADD_VALUE + A_QUANTITY * A_SIMPLE_ACTION.currentValeur(), portefeuille.getValue()),
                () -> assertEquals(portefeuilleMapShouldBe, portefeuille.getListeActions()));
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
    void testGetValueActionsShouldWork() {
        Portefeuille portefeuille = new Portefeuille();
        assertDoesNotThrow(() -> portefeuille.getValueActions());
    }

    @Test
    void testGetValueActionsSimpleWithCorrectJour() {
        // Création d'un portefeuille vide
        Portefeuille p1 = new Portefeuille();
        // Création d'un portefeuilles d'actions
        Portefeuille p2 = new Portefeuille();

        // Initialisation de la liste d'actions
        HashMap<Action, Integer> actions = new HashMap<>();

        // Création des actions simples
        ActionSimple a1 = new ActionSimple(ACTION_NAME_1);
        ActionSimple a2 = new ActionSimple(ACTION_NAME_2);

        // Création de jours
        Jour j = new Jour(ANNEE_1, JOUR_1);
        Jour j0 = new Jour(ANNEE_2, JOUR_2);

        // Ajout de cours aux actions
        a1.enrgCours(j, COURS_1);
        a2.enrgCours(j0, COURS_2);

        // Définition des quantités des actions dans le portefeuille
        actions.put(a1, QUANTITE_1);
        actions.put(a2, QUANTITE_2);

        // maj du portefeuile
        p2.setListeActions(actions);

        // Récupération de la valeur des actions
        final float VALUE_ACTION_P1 = p1.getValueActions(j);
        final float VALUE_ACTION_P2 = p2.getValueActions(j);

        // Vérification de la valeur des actions de chaque portefeuille
        assertAll(
                () -> assertEquals(0, VALUE_ACTION_P1),
                () -> assertEquals(500F, VALUE_ACTION_P2));
    }

    @Test
    void testGetValueActionsComposeeWithCorrectJour() {
        // Créatino des actions
        ActionSimple a1 = new ActionSimple(ACTION_NAME_1);
        ActionSimple a2 = new ActionSimple(ACTION_NAME_2);

        // Ajout de cours aux actions
        Jour j = new Jour(ANNEE_1, JOUR_1);
        Jour j0 = new Jour(ANNEE_2, JOUR_2);
        a1.enrgCours(j, COURS_1);
        a2.enrgCours(j0, COURS_2);

        // Création de l'action composée
        List<ActionSimple> actionsSimple = List.of(a1, a2);
        List<Float> fractions = List.of(FRACTION_1, FRACTION_2);
        ActionComposee ac1 = new ActionComposee(ACTION_COMPOSEE_LIB, actionsSimple, fractions);

        // Création du portefeuille & Ajout des actions au portefeuille
        HashMap<Action, Integer> actions = new HashMap<>();
        actions.put(ac1, 2); // définition de la quantité

        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setListeActions(actions);

        assertEquals(120F, portefeuille.getValueActions(j));
    }
}
