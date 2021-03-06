package com.myRPC.Netty_Center;

import com.myRPC.LoadBalance.Impl.RoundRobinLoadBalancer;
import com.myRPC.Rpc_Center.AbstractRpcServer;
import com.myRPC.Rpc_Center.RpcServer;
import com.myRPC.enum_util.RpcError;
import com.myRPC.exception.RpcException;
import com.myRPC.service.ServiceProvider;
import com.myRPC.service.ServiceRegistry;
import com.myRPC.service.impl.BalanceNacosServiceRegistry;
import com.myRPC.service.impl.NacosServiceRegistry;
import com.myRPC.service.impl.ServiceProviderImpl;
import com.myRPC.util.*;
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

import java.net.InetSocketAddress;

public class NettyServer extends AbstractRpcServer {

    private static final Logger logger= LoggerFactory.getLogger(NettyServer.class);

    private final String host;
    private final int port;
    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;
    private CommonSerializer serializer;

    public NettyServer(String host, int port,Integer serializer) {
        this.host = host;
        this.port = port;
        serviceRegistry = new BalanceNacosServiceRegistry(new RoundRobinLoadBalancer());
        serviceProvider = new ServiceProviderImpl();
        this.serializer=CommonSerializer.getByCode(serializer);
        scanServices();
    }


    @Override
    public <T> void publishService(T service, String serviceName) {
        serviceProvider.addServiceProvider(service,serviceName);
        serviceRegistry.register(serviceName,new InetSocketAddress(host,port));
    }

    @Override
    public void start() {

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
            ChannelFuture future = serverBootstrap.bind(host,port).sync();
            ShutdownHook.getShutdownHook().addClearAllHook();
            future.channel().closeFuture().sync();
        }catch (InterruptedException e) {
                            logger.error("?????????????????????????????????: ", e);
                        } finally {
                            bossGroup.shutdownGracefully();
                            workerGroup.shutdownGracefully();
                        }


    }



    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer=serializer;
    }
}
