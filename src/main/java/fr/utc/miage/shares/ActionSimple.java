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
import java.util.TreeMap;

/**
 * Allows the creation of simple Action objects.
 *
 * @author David Navarre &lt;David.Navarre at irit.fr&gt;
 */
public class ActionSimple extends Action {

    private static final int DEFAULT_ACTION_VALUE = 0;
    private Jour dernierJourModif = new Jour(1, 1);

    // attribut lien
    private final TreeMap<Jour, Float> mapCours;

    // constructeur
    public ActionSimple(final String libelle) {
        // Action simple initialisée comme 1 action
        super(libelle);
        // init spécifique
        this.mapCours = new TreeMap<>();
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
            if (j.compareTo(dernierJourModif) > 0) {
                dernierJourModif = j;
            } else {

                throw new IllegalArgumentException(
                        "Le jour de l'enregistrement du cours doit être postérieur au dernier jour ajouté");
            }
            this.mapCours.put(j, v);
        } else {
            throw new IllegalArgumentException("Le cours de l'action pour ce jour existe déjà");
        }
    }

    @Override
    public String visualiserAction() {
        return "[" + this.getLibelle() + "] Valeur : " + (double) this.valeur(dernierJourModif) + "€";
    }

    @Override
    public float valeur(final Jour j) {
        // si jour existe
        if (this.mapCours.containsKey(j)) {
            return this.mapCours.get(j);
        }
        // si le jour n'existe pas, on regarde si il y a une valeur avant, si il n'y a
        // pas de jour alors on renvoie 0
        else if (this.mapCours.isEmpty()) {
            return DEFAULT_ACTION_VALUE;
        } else {
            // Récupération du dernier jour existant avant mon jour j
            Jour lastJBeforeJ = mapCours.lowerKey(j);
            // Si il existe on renvoie la valeur, sinon on renvoie 0
            if (lastJBeforeJ != null) {
                return mapCours.get(lastJBeforeJ);
            }
            return DEFAULT_ACTION_VALUE;
        }
    }

    @Override
    public float currentValeur() {
        return valeur(dernierJourModif);
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

    public Map<Jour, Float> getMapCours() {
        return mapCours;
    }

}
