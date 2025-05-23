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

import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portefeuille {

    private double value;
    private HashMap<Action, Integer> listeActions;

    public Portefeuille() {
        this.value = 0;
        this.listeActions = new HashMap<>();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String visuPortefeuille() {
        return "Valeur du portefeuille : " + this.value + " €";
    }

    public float getValueActions() {
        float somme = 0;
        for (Map.Entry<Action, Integer> entry : listeActions.entrySet()) {
            somme += entry.getKey().currentValeur() * entry.getValue();
        }
        return somme;
    }

    public float getValueActions(Jour j) {
        float somme = 0F;

        // Parcours de chaque action du portefeuille
        for (Map.Entry<Action, Integer> entry : listeActions.entrySet()) {

            // Si action simple
            if (entry.getKey().getClass() == ActionSimple.class) {
                System.out.println("[DEBUG] Est ActionSimple");

                ActionSimple as = (ActionSimple) entry.getKey();

                System.out.println("Key : " + entry.getKey());
                System.out.println(as.getMapCours());

                System.out.println("qt : " + entry.getValue());
                somme += entry.getKey().valeur(j) * entry.getValue();

            }
            // Si action composée
            else if (entry.getKey().getClass() == ActionComposee.class) {
                System.out.println("[DEBUG] Est ActionComposee");

                ActionComposee ac = (ActionComposee) entry.getKey();

                System.out.println("Key : " + entry.getKey());
                System.out.println("qt : " + entry.getValue());

                // Parcours de chaque action simple de l'action composée
                for (HashMap.Entry<ActionSimple, Float> mapActsFract : ac.getMapActionsSimple().entrySet()) {
                    // valeur de l'action à j * pourcentage de l'action
                    somme += mapActsFract.getKey().valeur(j) * mapActsFract.getValue();
                }
                // Multiplication de la somme * la quantité de l'action composée dans le
                // portefeuile
                somme *= entry.getValue();
            }

            System.out.println("valeur key au jour j : " + somme + "\n");
        }
        return Math.round(somme * 10) / 10.0f;
    }

    public void ajouterDesFonds(double valeur) {
        if (valeur < 0) {
            throw new IllegalArgumentException("Merci de saisir un montant supérieur à 0");
        }
        this.value = this.value + valeur;
    }

    public HashMap<Action, Integer> getListeActions() {
        return listeActions;
    }

    public void setListeActions(HashMap<Action, Integer> listeActions) {
        HashMap<Action, Integer> deepCopy = new HashMap<>();
        for (HashMap.Entry<Action, Integer> entry : listeActions.entrySet()) {
            if (entry.getKey().getClass() == ActionSimple.class) {
                ActionSimple acs = (ActionSimple) entry.getKey();
                ActionSimple new_acs = new ActionSimple(acs.getLibelle());
                for (Map.Entry<Jour, Float> i : acs.getMapCours().entrySet()) {
                    new_acs.enrgCours(i.getKey(), i.getValue());
                }
                deepCopy.put(new_acs, entry.getValue());

            } else {
                ActionComposee ac = (ActionComposee) entry.getKey();
                deepCopy.put(new ActionComposee(entry.getKey().getLibelle(), ac.getActions(), ac.getFractions()),
                        entry.getValue());
            }
        }
        this.listeActions = deepCopy;
    }

}