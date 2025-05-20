package fr.utc.miage.shares;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ActionSimpleTest {
    
    private static final String FOO_SHARE1 = "Foo Share 1";

    @Test
    void testEnregistrerCoursActionShouldWork(){
        final ActionSimple action = new ActionSimple(FOO_SHARE1);
        final ActionSimple action2 = new ActionSimple(FOO_SHARE1);

        final Jour jour = new Jour(2025, 1 );

        action2.enrgCours(jour, 2);

        Assertions.assertAll("Set",
            () -> Assertions.assertDoesNotThrow(() ->  action.enrgCours(jour, 3f)), // Premier enregistrement 
            () -> Assertions.assertDoesNotThrow(() ->  action2.enrgCours(jour2, 3f))  // Second enregistrement
        );
    }

    @Test
    void testEnregistrerCoursActionWithIncorrectParametersShouldThrowException(){
        final ActionSimple action = new ActionSimple(FOO_SHARE1);

        final Jour jour = new Jour(2025, 1);
        final Jour jour2 = new Jour(2025, 5);

        action.enrgCours(jour, 2);

        Assertions.assertAll("Set",
            () -> Assertions.assertThrows(IllegalArgumentException.class, () -> action.enrgCours(jour, 3f)),  // Même jour
            () -> Assertions.assertThrows(IllegalArgumentException.class, () ->  action.enrgCours(jour2, 0f)),  // <=0
            () -> Assertions.assertThrows(IllegalArgumentException.class, () ->  action.enrgCours(jour2, -5f))  // <= 0
        );
    }
}
