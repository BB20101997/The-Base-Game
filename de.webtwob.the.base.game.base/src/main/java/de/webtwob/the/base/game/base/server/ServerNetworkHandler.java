package de.webtwob.the.base.game.base.server;

import de.webtwob.the.base.game.api.interfaces.IGameInstance;
import de.webtwob.the.base.game.api.network.IMessage;
import de.webtwob.the.base.game.api.network.INetworkHandler;
import de.webtwob.the.base.game.api.util.Either.Right;
import de.webtwob.the.base.game.base.network.SocketChannelChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetAddress;

/**
 * Created by BB20101997 on 25. Jul. 2018.
 */
public class ServerNetworkHandler implements INetworkHandler.IServerNetworkHandler {

    private static final System.Logger                 LOGGER = System.getLogger(ServerNetworkHandler.class.getName());
    private final        InetAddress                   listenAddress;
    private final        int                           listenPort;
    private final        IGameInstance.IServerInstance instance;

    public ServerNetworkHandler(final InetAddress address, final int port, final IGameInstance.IServerInstance instance) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("Port was outside of valid range (0-65535) with value " + port + " !");
        }
        listenPort = port;
        listenAddress = address;
        this.instance = instance;
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup   = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            LOGGER.log(System.Logger.Level.INFO, "Initiating Network Bootstrap");
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new SocketChannelChannelInitializer(new Right<>(instance)))
                     .option(ChannelOption.SO_BACKLOG, 128)
                     .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future;
            if (listenAddress == null) {
                future = bootstrap.bind(listenPort).sync();
            } else {
                future = bootstrap.bind(listenAddress, listenPort).sync();
            }
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void runNetworkHandler() {
        try {
            this.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void sendToAll(final IMessage message) {

    }
}
