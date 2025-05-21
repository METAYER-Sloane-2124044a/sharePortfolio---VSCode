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

import java.util.HashMap;
import java.util.Map;

public class Portefeuille {

    private double value;
    private Map<Action, Integer> listeActions;

    public Portefeuille() {
        this.value = 0;
        this.listeActions = new HashMap<>();
    }

    public double getValue() {
        return value;
    }

    public Map<Action, Integer> getListeActions() {
        return listeActions;
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

    public void ajouterDesFonds(double valeur) {
        if (valeur < 0) {
            throw new IllegalArgumentException("Merci de saisir un montant supérieur à 0");
        }
        this.value = this.value + valeur;
    }

    public void vendreActions(Action action, int quantity) {
        if(!listeActions.containsKey(action)) {
            throw new IllegalArgumentException("Vous essayez de vendre une action qui n'est pas présente dans votre portefeuille.");
        }
        if(listeActions.get(action) < quantity) {
            throw new IllegalArgumentException("Vous ne pouvez pas vendre plus d'action que ce que vous avez dans votre portefeuille");
        }
        if(listeActions.get(action) == quantity) {
            listeActions.remove(action);
        } else {
            listeActions.replace(action, listeActions.get(action) - quantity);
        }
        this.ajouterDesFonds(quantity*action.currentValeur());
    }
}