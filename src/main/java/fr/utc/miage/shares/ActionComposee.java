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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionComposee extends Action {

    private static final int DEFAULT_ACTION_VALUE = 0;
    private List<ActionSimple> actions = new ArrayList<>();
    private List<Float> fractions = new ArrayList<>();

    // attribut lien
    private final Map<Jour, Float> mapCours;

    // constructeur
    public ActionComposee(final String libelle, List<ActionSimple> aListOfActions, List<Float> aListOfFractions) {
        // Action simple initialisée comme 1 action
        super(libelle);

        Float sommeFractions = Float.valueOf(0);

        if (aListOfActions.size() != aListOfFractions.size())
            throw new IllegalArgumentException("Les deux listes doivent être de même longueur.");
        if (aListOfActions.size() < 2)
            throw new IllegalArgumentException("La liste d'actions doit avoir une longueur supérieure à 1.");
        // init spécifique
        this.mapCours = new HashMap<>();

        for (ActionSimple anAction : aListOfActions) {
            this.actions.add(anAction);
        }

        for (Float aFraction : aListOfFractions) {
            if (aFraction <= 0 || aFraction >= 1)
                throw new IllegalArgumentException("La fraction d'action doit être comprise entre 0 et 1 non inclus.");

            sommeFractions += aFraction;

            this.fractions.add(aFraction);
        }

        if (sommeFractions != 1) {
            throw new IllegalArgumentException("La somme des fractions doit être égale à 1 !");
        }

    }

    public List<ActionSimple> getActions() {
        return actions;
    }

    public List<Float> getFractions() {
        return fractions;
    }

    public Map<ActionSimple, Float> getMapActionsSimple() {
        Map<ActionSimple, Float> mapActionsSimple = new HashMap<>();

        List<ActionSimple> listActions = getActions();
        List<Float> listFractions = getFractions();

        for (int idx = 0; idx < actions.size(); ++idx) {
            mapActionsSimple.put(listActions.get(idx), listFractions.get(idx));
        }
        return mapActionsSimple;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((actions == null) ? 0 : actions.hashCode());
        result = prime * result + ((fractions == null) ? 0 : fractions.hashCode());
        result = prime * result + ((mapCours == null) ? 0 : mapCours.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ActionComposee other = (ActionComposee) obj;
        if (actions == null) {
            if (other.actions != null)
                return false;
        } else if (!actions.equals(other.actions))
            return false;
        if (fractions == null) {
            if (other.fractions != null)
                return false;
        } else if (!fractions.equals(other.fractions))
            return false;
        if (mapCours == null) {
            if (other.mapCours != null)
                return false;
        } else if (!mapCours.equals(other.mapCours))
            return false;
        return true;
    }

    @Override
    public float valeur(Jour j) {
        return this.mapCours.getOrDefault(j, Float.valueOf(DEFAULT_ACTION_VALUE));
    }

    @Override
    public String visualiserAction() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visualiserAction'");
    }

    @Override
    public float currentValeur() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'currentValeur'");
    }

}
