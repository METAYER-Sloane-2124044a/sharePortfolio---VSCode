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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ActionSimpleTest {
    
    private static final String FOO_SHARE1 = "Foo Share 1";
    private static final String FOO_SHARE2 = "Foo Share 2";
    private static final Jour A_DAY = new Jour(2025,100);
    private static final float A_VALUE_FOR_ACTION = 150.0F;

    @Test
    void testEnregistrerCoursActionShouldWork(){
        final ActionSimple action = new ActionSimple(FOO_SHARE1);
        final ActionSimple action2 = new ActionSimple(FOO_SHARE1);

        final Jour jour = new Jour(2025, 1);
        final Jour jour2 = new Jour(2025, 20);

        action2.enrgCours(jour, 2);

        Assertions.assertAll("Set",
            () -> Assertions.assertDoesNotThrow(() ->  action.enrgCours(jour, 3f)), // Premier enregistrement 
            () -> Assertions.assertDoesNotThrow(() ->  action2.enrgCours(jour2, 3f))  // Second enregistrement
        );
    }

    @Test
    void testEnregistrerCoursActionWithIncorrectParametersShouldThrowException(){
        final ActionSimple action = new ActionSimple(FOO_SHARE1);

        final Jour jour0 = new Jour(2024, 1);
        final Jour jour1 = new Jour(2025, 1);
        final Jour jour2 = new Jour(2025, 5);

        action.enrgCours(jour1, 2);

        Assertions.assertAll("Set",
            () -> Assertions.assertThrows(IllegalArgumentException.class, () -> action.enrgCours(jour1, 3f)),  // Même jour
            () -> Assertions.assertThrows(IllegalArgumentException.class, () ->  action.enrgCours(jour0, 3f)),  // Jour avant dernier jour
            () -> Assertions.assertThrows(IllegalArgumentException.class, () ->  action.enrgCours(jour2, 0f)),  // <=0
            () -> Assertions.assertThrows(IllegalArgumentException.class, () ->  action.enrgCours(jour2, -5f))  // <= 0
        );
    }

    @Test
    void testHashcodeEqualsObjectShouldBeSame() {
        ActionSimple a1 = new ActionSimple(FOO_SHARE1);
        ActionSimple a2 = new ActionSimple(FOO_SHARE1);

        a1.enrgCours(A_DAY, A_VALUE_FOR_ACTION);
        a2.enrgCours(A_DAY, A_VALUE_FOR_ACTION);

        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    void testEquals_sameInstance_shouldReturnTrue() {
        ActionSimple a1 = new ActionSimple(FOO_SHARE1);
        assertEquals(a1, a1);
    }

    @Test
    void testEquals_null_shouldReturnFalse() {
        ActionSimple a1 = new ActionSimple(FOO_SHARE1);
        assertNotEquals(null, a1);
    }

    @Test
    void testEquals_differentClass_shouldReturnFalse() {
        ActionSimple a1 = new ActionSimple(FOO_SHARE1);
        final String randomString = "some string"; 
        assertNotEquals(a1, randomString);
    }

    @Test
    void testEquals_differentLibelle_shouldReturnFalse() {
        ActionSimple a1 = new ActionSimple(FOO_SHARE1);
        ActionSimple a2 = new ActionSimple(FOO_SHARE2);
        a1.enrgCours(A_DAY, A_VALUE_FOR_ACTION);
        a2.enrgCours(A_DAY, A_VALUE_FOR_ACTION);

        assertNotEquals(a1, a2);
    }

    @Test
    void testEquals_differentDernierJour_shouldReturnFalse() {
        ActionSimple a1 = new ActionSimple(FOO_SHARE1);
        ActionSimple a2 = new ActionSimple(FOO_SHARE1);

        final Jour anotherDay = new Jour(2025, 101);
        a1.enrgCours(A_DAY, A_VALUE_FOR_ACTION);
        a2.enrgCours(anotherDay, A_VALUE_FOR_ACTION);

        assertNotEquals(a1, a2);
    }

    @Test
    void testEquals_differentMapCours_shouldReturnFalse() {
        ActionSimple a1 = new ActionSimple(FOO_SHARE1);
        ActionSimple a2 = new ActionSimple(FOO_SHARE1);

        final float anotherValueForAction = 200.0F;
        a1.enrgCours(A_DAY, A_VALUE_FOR_ACTION);
        a2.enrgCours(A_DAY, anotherValueForAction);

        assertNotEquals(a1, a2);
    }

    @Test
    void testEquals_allFieldsSame_shouldReturnTrue() {
        ActionSimple a1 = new ActionSimple(FOO_SHARE1);
        ActionSimple a2 = new ActionSimple(FOO_SHARE1);

        a1.enrgCours(A_DAY, A_VALUE_FOR_ACTION);
        a2.enrgCours(A_DAY, A_VALUE_FOR_ACTION);

        assertEquals(a1, a2);
    }
}
