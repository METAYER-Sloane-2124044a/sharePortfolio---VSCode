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
    
    private static final String DEFAULT_COMPOSED_ACTION_NAME = "Action composée Test";

    private static final List<ActionSimple> DEFAULT_LIST_ACTIONS = List.of(new ActionSimple(DEFAULT_ACTION_NAME1), new ActionSimple(DEFAULT_ACTION_NAME2), new ActionSimple(DEFAULT_ACTION_NAME3));
    private static final List<Float> DEFAULT_LIST_FRACTIONS = List.of(0.3F, 0.3F,0.4F);

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

        List<ActionSimple> actions2 = List.of(new ActionSimple(DEFAULT_ACTION_NAME1),new ActionSimple(DEFAULT_ACTION_NAME2));
        Assertions.assertThrows(IllegalArgumentException.class,()->{
        new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME,actions2,DEFAULT_LIST_FRACTIONS);
        });

        List<Float> fractions2 = List.of(0.5F,0.5F);
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME,DEFAULT_LIST_ACTIONS,fractions2);
        });

        List<ActionSimple> actions1 = List.of(new ActionSimple(DEFAULT_ACTION_NAME1));
        List<Float> fractions1 = List.of(1F);
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME,actions1,fractions1);
        });

        List<Float> fractions3_1 = List.of(0.5F,0.5F,2F);
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME,DEFAULT_LIST_ACTIONS,fractions3_1);
        });

        List<Float> fractions3_2 = List.of(0.5F,0.5F,0.5F);
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME,DEFAULT_LIST_ACTIONS,fractions3_2);
        });

        List<Float> fractions3_3 = List.of(0.5F,0.7F,-0.2F);
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            new ActionComposee(DEFAULT_COMPOSED_ACTION_NAME,DEFAULT_LIST_ACTIONS,fractions3_3);
        });

    }
}
