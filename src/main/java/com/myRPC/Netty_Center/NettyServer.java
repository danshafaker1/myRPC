package com.myRPC.Netty_Center;

import com.myRPC.Rpc_Center.RpcServer;
import com.myRPC.util.CommonDecoder;
import com.myRPC.util.CommonEncoder;
import com.myRPC.util.JsonSerializer;
import com.myRPC.util.KryoSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServer implements RpcServer {

    private static final Logger logger= LoggerFactory.getLogger(NettyServer.class);





    @Override
    public void start(int port) {

        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();


        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .option(ChannelOption.SO_BACKLOG,256)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(1024,12,4,0,0));
                            pipeline.addLast(new CommonEncoder(new KryoSerializer()));
                            pipeline.addLast(new CommonDecoder());
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        }catch (InterruptedException e) {
                            logger.error("启动服务器时有错误发生: ", e);
                        } finally {
                            bossGroup.shutdownGracefully();
                            workerGroup.shutdownGracefully();
                        }


    }
}
