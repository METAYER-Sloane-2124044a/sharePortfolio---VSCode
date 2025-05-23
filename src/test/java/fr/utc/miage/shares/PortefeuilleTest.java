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

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PortefeuilleTest {

    private static final String PORTFOLIO_VALUE = "Valeur du portefeuille : 500.0 €";
    private static final double INITIAL_VALUE = 500.0;
    private static final double ADD_VALUE = 100.0;
    private static final double ADD_VALUE_NEGATIF = -100.0;
    private static final String ACTION_NAME_1 = "Action 1";
    private static final String ACTION_NAME_2 = "Action 2";
    private static final String ACTION_COMPOSEE_LIB = "Action composee 1";

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
        Jour j = new Jour(2025, 56);
        Jour j0 = new Jour(2026, 34);

        // Ajout de cours aux actions
        a1.enrgCours(j, 100F);
        a2.enrgCours(j0, 280F);

        // Définition des quantités des actions dans le portefeuille
        actions.put(a1, 5);
        actions.put(a2, 17);

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
        Jour j = new Jour(2025, 56);
        Jour j0 = new Jour(2026, 34);
        a1.enrgCours(j, 100F);
        a2.enrgCours(j0, 280F);

        // Création de l'action composée
        List<ActionSimple> actionsSimple = List.of(a1, a2);
        List<Float> fractions = List.of(0.6F, 0.4F);
        ActionComposee ac1 = new ActionComposee(ACTION_COMPOSEE_LIB, actionsSimple, fractions);

        // Création du portefeuille & Ajout des actions au portefeuille
        HashMap<Action, Integer> actions = new HashMap<>();
        actions.put(ac1, 2); // définition de la quantité

        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setListeActions(actions);

        assertEquals(120F, portefeuille.getValueActions(j));
    }
}
