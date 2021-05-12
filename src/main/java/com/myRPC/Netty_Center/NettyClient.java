package com.myRPC.Netty_Center;

import com.myRPC.Rpc_Center.RpcClient;
import com.myRPC.Rpc_Center.RpcRequest;
import com.myRPC.Rpc_Center.RpcResponse;
import com.myRPC.enum_util.RpcError;
import com.myRPC.exception.RpcException;
import com.myRPC.service.ServiceRegistry;
import com.myRPC.service.impl.NacosServiceRegistry;
import com.myRPC.util.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

public class NettyClient implements RpcClient {


    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);


    private static final Bootstrap bootstrap;
    private CommonSerializer serializer;
    private ServiceRegistry serviceRegistry;




    public NettyClient(){
        this.serviceRegistry=new NacosServiceRegistry();
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {

        if(serializer==null){
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND.getMsg());
        }
        AtomicReference<Object> result = new AtomicReference<>(null);

        try {
            InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(rpcRequest.getInterfaceName());
            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
            if(channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if(future1.isSuccess()) {
                        logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        logger.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse.getData();
            }
        } catch (InterruptedException e) {
            logger.error("发送消息时有错误发生: ", e);
        }
        return null;
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer=serializer;
    }


}
