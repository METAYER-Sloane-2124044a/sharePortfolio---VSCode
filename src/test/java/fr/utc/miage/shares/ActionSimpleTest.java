package fr.utc.miage.shares;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ActionSimpleTest {
    
    private static final String DEFAULT_ACTION_NAME = "Foo Share 1";

    @Test
    void testEnregistrerCoursActionShouldWork(){
        final ActionSimple action = new ActionSimple(DEFAULT_ACTION_NAME);
        final ActionSimple action2 = new ActionSimple(DEFAULT_ACTION_NAME);

        final Jour jour = new Jour(2025, 1 );

        action2.enrgCours(jour, 2);

        Assertions.assertAll("Set",
            () -> Assertions.assertDoesNotThrow(() ->  action.enrgCours(jour, 3f)), // Premier enregistrement 
            () -> Assertions.assertDoesNotThrow(() ->  action2.enrgCours(jour2, 3f))  // Second enregistrement
        );
    }

    @Test
    void testEnregistrerCoursActionWithIncorrectParametersShouldThrowException(){
        final ActionSimple action = new ActionSimple(DEFAULT_ACTION_NAME);

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
        final Jour janv20 = new Jour(2025, 20);
        final float VALEUR_ACTION_TEST = 50F;
        actionTest.enrgCours(janv20, VALEUR_ACTION_TEST);

        final String STR_ACTION_TEST = actionTest.visualiserAction();
        final String STR_SHOULD_BE = "[" + DEFAULT_ACTION_NAME + "] Valeur : " + (double)VALEUR_ACTION_TEST + "€";

        assertEquals(STR_SHOULD_BE, STR_ACTION_TEST);
    }

}
