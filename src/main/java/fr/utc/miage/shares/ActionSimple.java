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

/**
 * Allows the creation of simple Action objects.
 *
 * @author David Navarre &lt;David.Navarre at irit.fr&gt;
 */
public class ActionSimple extends Action {

    private static final int DEFAULT_ACTION_VALUE = 0;
    private Jour dernierJourModif = new Jour(1, 1);

    // attribut lien
    private final Map<Jour, Float> mapCours;

    // constructeur
    public ActionSimple(final String libelle) {
        // Action simple initialisée comme 1 action
        super(libelle);
        // init spécifique
        this.mapCours = new HashMap<>();
    }

    public Jour getDernierJourModif() {
        return dernierJourModif;
    }

    // enrg possible si pas de cours pour ce jour
    public void enrgCours(final Jour j, final float v) {
        if (v <= 0) {
            throw new IllegalArgumentException("Le cours de l'action doit être strictement supérieur à zéro");
        }

        if (!this.mapCours.containsKey(j)) {
            this.mapCours.put(j, v);
            if (j.compareTo(dernierJourModif) > 0) {
                dernierJourModif = j;
            } else {
                throw new IllegalArgumentException(
                        "Le jour de l'enregistrement du cours doit être postérieur au dernier jour ajouté");
            }
        } else {
            throw new IllegalArgumentException("Le cours de l'action pour ce jour existe déjà");
        }
    }

    public void resetCours() {
        this.mapCours.clear();
        this.dernierJourModif = new Jour(1, 1);
    }

    @Override
    public String visualiserAction() {
        return "[" + this.getLibelle() + "] Valeur : " + (double) this.valeur(dernierJourModif) + "€";
    }

    @Override
    public float valeur(final Jour j) {
        if (this.mapCours.containsKey(j)) {
            return this.mapCours.get(j);
        } else {
            return DEFAULT_ACTION_VALUE;
        }
    }

    @Override
    public int hashCode() {
        int result = getLibelle().hashCode();
        result = 31 * result + dernierJourModif.hashCode();
        result = 31 * result + mapCours.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        ActionSimple other = (ActionSimple) obj;

        if (!this.getLibelle().equals(other.getLibelle()))
            return false;

        if (!this.dernierJourModif.equals(other.dernierJourModif))
            return false;

        return this.mapCours.equals(other.mapCours);
    }

}
