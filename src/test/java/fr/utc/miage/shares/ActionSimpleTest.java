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
            () -> Assertions.assertDoesNotThrow(() ->  action.enrgCours(jour, 3)), // Premier enregistrement 
            () -> Assertions.assertDoesNotThrow(() ->  action2.enrgCours(jour, 3))  // Second enregistrement
        );
    }
}
