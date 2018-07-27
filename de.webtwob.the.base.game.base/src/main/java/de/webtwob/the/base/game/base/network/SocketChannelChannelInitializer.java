package de.webtwob.the.base.game.base.network;

import de.webtwob.the.base.game.api.interfaces.IGameInstance;
import de.webtwob.the.base.game.api.util.Either;
import de.webtwob.the.base.game.base.network.messages.CloseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Created by BB20101997 on 26. Jul. 2018.
 */
public class SocketChannelChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final System.Logger LOGGER = System.getLogger(SocketChannelChannelInitializer.class.getName());

    Either<IGameInstance.IClientInstance, IGameInstance.IServerInstance> instance;

    public SocketChannelChannelInitializer(Either<IGameInstance.IClientInstance, IGameInstance.IServerInstance> instance) {
        this.instance = instance;
    }

    @Override
    protected void initChannel(final SocketChannel ch) {
        ch.pipeline()
          .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 0))
          .addLast("decoder", new MessageDecoder(Either.value(instance)))
          .addLast("closeHandler", new ChannelInboundHandlerAdapter() {
              @Override
              public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
                  if (!(msg instanceof CloseMessage)) {
                      super.channelRead(ctx, msg);
                      return;
                  }
                  LOGGER.log(System.Logger.Level.INFO,"Other side is closing with reason:"+((CloseMessage)msg).reason());
                  ctx.close();
                  channelReadComplete(ctx);
              }
          })
          .addLast("handshakeHandler",new HandshakeHandler(instance))
          .addLast("handler", new GameMessageHandler(instance))
          .addLast("encoder", new MessageEncoder(Either.value(instance)))
          .addLast(new LengthFieldPrepender(4));
    }
}
