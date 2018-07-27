package de.webtwob.the.base.game.base.client;

import de.webtwob.the.base.game.api.network.IMessage;
import de.webtwob.the.base.game.api.network.INetworkHandler;
import de.webtwob.the.base.game.api.util.Either.Left;
import de.webtwob.the.base.game.base.network.SocketChannelChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class ClientNetworkHandler implements INetworkHandler.IClientNetworkHandler {

    private final InetAddress address;
    private final int port;
    private final ClientGameInstance clientGameInstance;

    public ClientNetworkHandler(final InetAddress address, final int port, final ClientGameInstance clientGameInstance) {

        this.address = address;
        this.port = port;
        this.clientGameInstance = clientGameInstance;
    }

    @Override
    public void runNetworkHandler() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                     .channel(NioSocketChannel.class)
                     .option(ChannelOption.SO_KEEPALIVE,true)
                     .handler(new SocketChannelChannelInitializer(new Left<>(clientGameInstance)));

        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void sendToServer(final IMessage message) {

    }
}
