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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ActionComposeeTest {

    private static final String DEFAULT_ACTION_NAME1 = "Action A";
    private static final String DEFAULT_ACTION_NAME2 = "Action B";
    private static final String DEFAULT_ACTION_NAME3 = "Action C";
    
    private static final float DEFAULT_ACTION_VALUE1 = 10F;

    private static final String DEFAULT_COMPOSED_ACTION_NAME = "Action composée Test";

    private static final List<ActionSimple> DEFAULT_LIST_ACTIONS = List.of(new ActionSimple(DEFAULT_ACTION_NAME1), new ActionSimple(DEFAULT_ACTION_NAME2), new ActionSimple(DEFAULT_ACTION_NAME3));
    private static final List<ActionSimple> ACTION_LIST_WITH_1_ACTION = List.of(new ActionSimple(DEFAULT_ACTION_NAME1));
    private static final List<ActionSimple> ACTION_LIST_WITH_2_ACTIONS = List.of(new ActionSimple(DEFAULT_ACTION_NAME1),new ActionSimple(DEFAULT_ACTION_NAME2));
    
    private static final List<Float> DEFAULT_LIST_FRACTIONS = List.of(0.3F, 0.3F,0.4F);
    private static final List<Float> FRACTION_LIST_WITH_ONE_FRACTION = List.of(1F);
    private static final List<Float> FRACTION_LIST_WITH_2_FRACTIONS = List.of(0.5F,0.5F);
    private static final List<Float> FRACTION_LIST_WITH_ONE_FRACTION_ABOVE_1 = List.of(0.5F,0.5F,2F);
    private static final List<Float> FRACTION_LIST_WITH_SUM_ABOVE_1 = List.of(0.5F,0.5F,0.5F);
    private static final List<Float> FRACTION_LIST_WITH_SUM_EQUAL_ONE_BUT_INVALID_FRACTION = List.of(0.5F,0.7F,-0.2F);
        
    private static final Jour DEFAULT_DAY1 = new Jour(2025, 1);
    private static final Jour DEFAULT_DAY2 = new Jour(2025, 10);

    @Test
    void testCreationActionComposeeShouldWork() {
        Assertions.assertDoesNotThrow(() -> {
            new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        });
    }


    @Test
    void testCreationActionComposeeShouldNotWork() {
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionComposee("",DEFAULT_LIST_ACTIONS,DEFAULT_LIST_FRACTIONS);
        });

        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionComposee(null,DEFAULT_LIST_ACTIONS,DEFAULT_LIST_FRACTIONS);
        });

        
        Assertions.assertThrows(IllegalArgumentException.class,()->{
        new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME,ACTION_LIST_WITH_2_ACTIONS,DEFAULT_LIST_FRACTIONS);
        });

        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME,DEFAULT_LIST_ACTIONS,FRACTION_LIST_WITH_2_FRACTIONS);
        });

        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME,ACTION_LIST_WITH_1_ACTION,FRACTION_LIST_WITH_ONE_FRACTION);
        });

        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME,DEFAULT_LIST_ACTIONS,FRACTION_LIST_WITH_ONE_FRACTION_ABOVE_1);
        });

        
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME,DEFAULT_LIST_ACTIONS,FRACTION_LIST_WITH_SUM_ABOVE_1);
        });

        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME,DEFAULT_LIST_ACTIONS,FRACTION_LIST_WITH_SUM_EQUAL_ONE_BUT_INVALID_FRACTION);
        });

    }

    @Test
    void testVisualiserActionComposeeShouldWork() {
        ActionComposee actionComp = new ActionComposee(DEFAULT_ACTION_NAME1, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        
        Assertions.assertNotEquals("", actionComp.visualiserAction());

        ActionSimple action1 = new ActionSimple(DEFAULT_ACTION_NAME1);
        action1.enrgCours(DEFAULT_DAY1, DEFAULT_ACTION_VALUE1);

        ActionSimple action2 = new ActionSimple(DEFAULT_ACTION_NAME2);
        action2.enrgCours(DEFAULT_DAY2, DEFAULT_ACTION_VALUE1);

        ActionComposee actionCompJour = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, List.of(action1, action2), FRACTION_LIST_WITH_2_FRACTIONS);
    
        Assertions.assertNotEquals("", actionCompJour.visualiserAction());

    }

    @Test
    void testEqualsReflexive() {
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        assertEquals(action1, action1, "An object should be equal to itself");
    }

    @Test
    void testEqualsSymmetric() {
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        assertEquals(action1, action2, "Equals should be symmetric");
        assertEquals(action2, action1, "Equals should be symmetric");
    }

    @Test
    void testEqualsTransitive() {
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action3 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        assertEquals(action1, action2, "Equals should be transitive");
        assertEquals(action2, action3, "Equals should be transitive");
        assertEquals(action1, action3, "Equals should be transitive");
    }

    @Test
    void testEqualsNull() {
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        Assertions.assertNotEquals(null, action1, "An object should not be equal to null");
    }

    @Test
    void testEqualsDifferentClass() {
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        Assertions.assertNotEquals(action1, "Some String", "An object should not be equal to an object of a different class");
    }

    @Test
    void testEqualsDifferentLibelle() {
        ActionComposee action1 = new ActionComposee("Libelle1", DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee("Libelle2", DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        Assertions.assertNotEquals(action1, action2, "Actions with different libelle should not be equal");
    }

    @Test
    void testEqualsDifferentActions() {
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, ACTION_LIST_WITH_2_ACTIONS, FRACTION_LIST_WITH_2_FRACTIONS);
        Assertions.assertNotEquals(action1, action2, "Actions with different actions list should not be equal");
    }

    @Test
    void testEqualsDifferentFractions() {
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        List<Float> otherFractions = List.of(0.2F, 0.3F, 0.5F);
        ActionComposee action2 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, otherFractions);
        Assertions.assertNotEquals(action1, action2, "Actions with different fractions should not be equal");
    }

    @Test
    void testHashCodeConsistency() {
        ActionComposee action = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        int initialHashCode = action.hashCode();
        // hashCode should be consistent across multiple invocations
        assertEquals(initialHashCode, action.hashCode(), "hashCode should be consistent");
        assertEquals(initialHashCode, action.hashCode(), "hashCode should be consistent on repeated calls");
    }

    @Test
    void testHashCodeEqualsContract() {
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        // If two objects are equal, their hashCodes must be equal
        assertEquals(action1, action2, "Objects should be equal");
        assertEquals(action1.hashCode(), action2.hashCode(), "Equal objects must have equal hashCodes");
    }

    @Test
    void testHashCodeDifferentForDifferentObjects() {
        ActionComposee action1 = new ActionComposee("Libelle1", DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee("Libelle2", DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        // It's not required, but likely that different objects have different hashCodes
        // This test just ensures that hashCodes are not always the same for different objects
        Assertions.assertNotEquals(action1, action2, "Objects should not be equal");
        Assertions.assertNotEquals(action1.hashCode(), action2.hashCode(), "Different objects likely have different hashCodes");
    }

    @Test
    void testEqualsSameObject() {
        ActionComposee action = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        Assertions.assertTrue(action.equals(action), "equals should return true when comparing the same object");
    }

    @Test
    void testEqualsNullObject() {
        ActionComposee action = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        Assertions.assertFalse(action.equals(null), "equals should return false when comparing with null");
    }

    @Test
    void testEqualsDifferentClassObject() {
        ActionComposee action = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        Assertions.assertFalse(action.equals("not an ActionComposee"), "equals should return false when comparing with different class");
    }

    @Test
    void testEqualsDifferentActionsList() {
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, ACTION_LIST_WITH_2_ACTIONS, FRACTION_LIST_WITH_2_FRACTIONS);
        Assertions.assertFalse(action1.equals(action2), "equals should return false for different actions list");
    }

    @Test
    void testEqualsDifferentFractionsList() {
        List<Float> otherFractions = List.of(0.2F, 0.3F, 0.5F);
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, otherFractions);
        Assertions.assertFalse(action1.equals(action2), "equals should return false for different fractions list");
    }

    @Test
    void testEqualsTrueForIdenticalObjects() {
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        Assertions.assertTrue(action1.equals(action2), "equals should return true for identical objects");
    }

    @Test
    void testEqualsWithSameReference() {
        ActionComposee action = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        Assertions.assertTrue(action.equals(action), "equals should return true for the same reference");
    }

    @Test
    void testEqualsWithNull() {
        ActionComposee action = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        Assertions.assertFalse(action.equals(null), "equals should return false when compared to null");
    }

    @Test
    void testEqualsWithDifferentClass() {
        ActionComposee action = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        Assertions.assertFalse(action.equals("not an ActionComposee"), "equals should return false when compared to a different class");
    }

    @Test
    void testEqualsWithDifferentActions() {
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, ACTION_LIST_WITH_2_ACTIONS, FRACTION_LIST_WITH_2_FRACTIONS);
        Assertions.assertFalse(action1.equals(action2), "equals should return false for different actions lists");
    }

    @Test
    void testEqualsWithDifferentFractions() {
        List<Float> otherFractions = List.of(0.2F, 0.3F, 0.5F);
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, otherFractions);
        Assertions.assertFalse(action1.equals(action2), "equals should return false for different fractions lists");
    }

    @Test
    void testEqualsWithIdenticalObjects() {
        ActionComposee action1 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME, DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        Assertions.assertTrue(action1.equals(action2), "equals should return true for identical objects");
    }

    @Test
    void testEqualsWithDifferentLibelle() {
        ActionComposee action1 = new ActionComposee("Libelle1", DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        ActionComposee action2 = new ActionComposee("Libelle2", DEFAULT_LIST_ACTIONS, DEFAULT_LIST_FRACTIONS);
        Assertions.assertFalse(action1.equals(action2), "equals should return false for different libelle");
    }




}
