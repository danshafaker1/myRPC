package com;

import com.myRPC.Netty_Center.NettyServer;
import com.myRPC.service.HelloService;
import com.myRPC.service.ServiceRegistry;
import com.myRPC.service.impl.DefaultServiceRegistry;
import com.myRPC.service.impl.HelloServiceImpl;

public class TestNettyServer {

    public static void main(String[] args) {

        HelloService helloService=new HelloServiceImpl();
        ServiceRegistry registry=new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server=new NettyServer();
        server.start(9999);


    }


}
