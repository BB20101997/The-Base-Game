package de.webtwob.the.base.game.api.gui;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Created by BB20101997 on 17. Jul. 2018.
 */
public class ColorTest {

    @Test
    public void expectExcpection() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Color.BASE.withAlpha(2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Color.BASE.withBlue(2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Color.BASE.withGreen(2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Color.BASE.withAlpha(2));
    }

}
