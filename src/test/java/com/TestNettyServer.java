package com;

import com.myRPC.Netty_Center.NettyServer;
import com.myRPC.service.HelloService;
import com.myRPC.service.ServiceProvider;
import com.myRPC.service.impl.ServiceProviderImpl;
import com.myRPC.service.impl.HelloServiceImpl;
import com.myRPC.util.KryoSerializer;
import com.myRPC.util.NacosUtil;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class TestNettyServer {

    public static void main(String[] args) {

        HelloService helloService=new HelloServiceImpl();
        InetSocketAddress inetSocketAddress=new InetSocketAddress("localhost",9999);
        NettyServer server=new NettyServer("localhost",9999);
        server.setSerializer(new KryoSerializer());
        server.publishService(helloService,HelloService.class);

    }


}
