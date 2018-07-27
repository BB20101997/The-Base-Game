package de.webtwob.the.base.game.api;

import de.webtwob.the.base.game.api.interfaces.IGameInstance;
import de.webtwob.the.base.game.api.interfaces.IGameLogic;
import de.webtwob.the.base.game.api.interfaces.IRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by BB20101997 on 18. Jul. 2018.
 */
class BaseModTest {

    @Test
    void create() {

        String id = new Random().ints('a','z'+1).limit(20).mapToObj(i->(char)i+"").collect(Collectors.joining());

        BaseMod mod = BaseMod.create().withID(id).with(m->new IGameInstance.IClientInstance() {
            @Override
            public IRenderer getRenderer() {
                return null;
            }

            @Override
            public BaseMod getBaseMod() {
                return null;
            }
        }, m->new IGameInstance.IServerInstance() {
            @Override
            public IGameLogic getGameLogic() {
                return null;
            }

            @Override
            public BaseMod getBaseMod() {
                return null;
            }
        });
        Assertions.assertEquals(id,mod.id());
        Assertions.assertNotEquals(mod.getClientInstance(),mod.getClientInstance());
        Assertions.assertNotEquals(mod.getServerInstance(),mod.getServerInstance());

    }
}
