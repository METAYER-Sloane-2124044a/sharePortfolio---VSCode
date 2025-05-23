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

public class Portefeuille {

    private double value;
    private HashMap<Action, Integer> listeAction = new HashMap<>();

    public Portefeuille() {
        this.value = 0;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public HashMap<Action, Integer> getListeAction() {
        return listeAction;
    }

    public String visuPortefeuille() {
        return "Valeur du portefeuille : " + this.value + " €";
    }

    public void ajouterDesFonds(double valeur) {
        if (valeur < 0) {
            throw new IllegalArgumentException("Merci de saisir un montant supérieur à 0");
        }
        this.value = this.value + valeur;
    }

    public void retirerDesFonds(double valeur) {
        if (valeur < 0) {
            throw new IllegalArgumentException("Merci de saisir un montant supérieur à 0");
        } else if (valeur > this.value) {
            throw new IllegalArgumentException("Merci de saisir un montant inférieur à la valeur du portefeuille");
        }
        this.value = this.value - valeur;
    }

    public void acheterAction(Action action, int nb, Jour j) {
        if (nb <= 0) {
            throw new IllegalArgumentException("Merci de saisir un nombre d'actions supérieur à 0");
        } else if (action.valeur(j) * nb > this.value) {
            throw new IllegalArgumentException("Merci de saisir un montant inférieur à la valeur du portefeuille");
        }
        int totalNb = nb;
        if (listeAction.get(action) != null) {
            totalNb += listeAction.get(action);
        }
        listeAction.put(action, totalNb);
        this.retirerDesFonds(action.valeur(j) * nb);
    }
}