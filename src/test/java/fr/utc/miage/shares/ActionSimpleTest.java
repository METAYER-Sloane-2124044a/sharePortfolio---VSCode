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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ActionSimpleTest {
    private static final float VALEUR_ACTION_TEST = 50F;
    private static final String DEFAULT_ACTION_NAME = "Foo Share 1";
    private static final Jour DEFAULT_DAY1 = new Jour(2025, 1);
    private static final Jour DEFAULT_DAY2 = new Jour(2025, 10);
    private static final Jour DEFAULT_DAY3 = new Jour(2025, 20);

    private static final float DEFAULT_ACTION_VALUE1 = 2F;
    private static final float DEFAULT_ACTION_VALUE2 = 3F;

    private static final float NEGATIVE_ACTION_VALUE = -2F;

    @Test
    void testEnregistrerCoursActionShouldWork(){
        final ActionSimple action = new ActionSimple(DEFAULT_ACTION_NAME);
        final ActionSimple action2 = new ActionSimple(DEFAULT_ACTION_NAME);

        action2.enrgCours(DEFAULT_DAY1, DEFAULT_ACTION_VALUE1);

        Assertions.assertAll("Set",
            () -> Assertions.assertDoesNotThrow(() ->  action.enrgCours(DEFAULT_DAY1, DEFAULT_ACTION_VALUE2)), // Premier enregistrement 
            () -> Assertions.assertDoesNotThrow(() ->  action2.enrgCours(DEFAULT_DAY2, DEFAULT_ACTION_VALUE2))  // Second enregistrement
        );
    }

    @Test
    void testEnregistrerCoursActionWithIncorrectParametersShouldThrowException(){
        final ActionSimple action = new ActionSimple(DEFAULT_ACTION_NAME);

        action.enrgCours(DEFAULT_DAY2, DEFAULT_ACTION_VALUE1);

        Assertions.assertAll("Set",
            () -> Assertions.assertThrows(IllegalArgumentException.class, () -> action.enrgCours(DEFAULT_DAY1, DEFAULT_ACTION_VALUE2)),  // Jour avant dernier jour
            () -> Assertions.assertThrows(IllegalArgumentException.class, () ->  action.enrgCours(DEFAULT_DAY2, DEFAULT_ACTION_VALUE2)),  // Même jour
            () -> Assertions.assertThrows(IllegalArgumentException.class, () ->  action.enrgCours(DEFAULT_DAY3, 0f)),  // valeur <= 0
            () -> Assertions.assertThrows(IllegalArgumentException.class, () ->  action.enrgCours(DEFAULT_DAY3, NEGATIVE_ACTION_VALUE))  // valeur <= 0
        );
    }

    @Test
    void testCreationActionSimpleShouldWork(){
        Assertions.assertDoesNotThrow(()->{
            new ActionSimple(DEFAULT_ACTION_NAME);
        });
    }
    
    @Test
    void testCreationActionSimpleShouldNotWork() {
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionSimple("");
        });

        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionSimple(null);
        });
    }

    @Test
    void testVisualiserActionSimpleShouldWork() {
        final ActionSimple actionTest = new ActionSimple(DEFAULT_ACTION_NAME);


        actionTest.enrgCours(DEFAULT_DAY1, VALEUR_ACTION_TEST);

        final String STR_ACTION_TEST = actionTest.visualiserAction();
        final String STR_SHOULD_BE = "[" + DEFAULT_ACTION_NAME + "] Valeur : " + (double)VALEUR_ACTION_TEST + "€";

        assertEquals(STR_SHOULD_BE, STR_ACTION_TEST);
    }

}
