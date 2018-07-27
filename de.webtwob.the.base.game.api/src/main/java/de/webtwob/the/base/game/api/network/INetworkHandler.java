package de.webtwob.the.base.game.api.network;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public interface INetworkHandler {

    void runNetworkHandler();

    interface IClientNetworkHandler extends INetworkHandler{
        void sendToServer(IMessage message);
    }

    interface IServerNetworkHandler extends INetworkHandler{
        void sendToAll(IMessage message);
        //TODO sendToPlayer, sendToDimension, sendToPosition etc.
    }

}
