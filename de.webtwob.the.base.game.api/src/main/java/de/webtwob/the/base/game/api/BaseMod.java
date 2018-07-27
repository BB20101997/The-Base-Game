package de.webtwob.the.base.game.api;

import de.webtwob.the.base.game.api.interfaces.IGameInstance;

import java.util.function.Function;

/**
 * Created by BB20101997 on 17. Jul. 2018.
 */
public final class BaseMod{

    private final String id;
    Function<BaseMod,IGameInstance.IClientInstance> clientInstanceSupplier;
    Function<BaseMod,IGameInstance.IServerInstance> serverInstanceSupplier;


    public interface StartBuildingBaseMod{
        BothSupplierMissing withID(String modId);
    }

    public interface ClientFunctionMissing <R>{
        R withClientSuppliert(Function<BaseMod,IGameInstance.IClientInstance> client);
    }

    public interface ServerFunctionMissing <R>{
        R withServerFunction(Function<BaseMod,IGameInstance.IServerInstance> supplier);
    }

    public interface BothSupplierMissing {

        BaseMod with(Function<BaseMod, IGameInstance.IClientInstance> clientInstance, Function<BaseMod, IGameInstance.IServerInstance> serverInstance);

        default ClientFunctionMissing<BaseMod> withServerSupplier(Function<BaseMod,IGameInstance.IServerInstance> server) {
            return client->with(client,server);
        }

        default ServerFunctionMissing<BaseMod> withClientFunction(Function<BaseMod,IGameInstance.IClientInstance> client){
                return server -> with(client,server);
        }
    }

    private BaseMod(final String id, Function<BaseMod, IGameInstance.IClientInstance> clientSupplier, Function<BaseMod, IGameInstance.IServerInstance> serverSupplier) {
        this.id = id;
        this.clientInstanceSupplier = clientSupplier;
        this.serverInstanceSupplier = serverSupplier;
    }

    public IGameInstance.IClientInstance getClientInstance(){
        return clientInstanceSupplier.apply(this);
    }

    public IGameInstance.IServerInstance getServerInstance(){
        return serverInstanceSupplier.apply(this);
    }

    public static StartBuildingBaseMod create(){
        return name -> (client,server) -> new BaseMod(name,client,server);
    }

    public String id(){
        return id;
    }

}
