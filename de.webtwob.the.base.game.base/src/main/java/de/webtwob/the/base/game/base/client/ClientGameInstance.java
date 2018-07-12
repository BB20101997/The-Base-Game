package de.webtwob.the.base.game.base.client;

import de.webtwob.the.base.game.api.IRenderer;
import de.webtwob.the.base.game.api.service.IBaseMod;
import de.webtwob.the.base.game.api.IGameInstance;
import de.webtwob.the.base.game.api.service.IMod;
import de.webtwob.the.base.game.base.RegistryRegistry;
import de.webtwob.the.base.game.base.TheBaseGameRenderer;

/**
 * Created by BB20101997 on 10. Jul. 2018.
 */
public class ClientGameInstance implements IGameInstance.IClientInstance {

    private       RegistryRegistry masterRegistry;
    private final IBaseMod         baseMode;
    private IRenderer renderer = new TheBaseGameRenderer();

    public static ClientGameInstance createInstance(IBaseMod baseMode){
        return new ClientGameInstance(baseMode);
    }

    private ClientGameInstance(final IBaseMod baseMode) {
        this.baseMode = baseMode;
    }

    @Override
    public IMod getBaseMod() {
        return baseMode;
    }

    @Override
    public IRenderer getRenderer() {
        return renderer;
    }
}
