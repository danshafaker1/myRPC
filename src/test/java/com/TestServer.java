package com;

import com.myRPC.Rpc_Center.RpcServer;
import com.myRPC.service.HelloService;
import com.myRPC.service.ServiceRegistry;
import com.myRPC.service.impl.DefaultServiceRegistry;
import com.myRPC.service.impl.HelloServiceImpl;

import java.net.ServerSocket;

public class TestServer {

    public static void main(String[] args) {

        //注册HelloService服务
        HelloService helloService=new HelloServiceImpl();
        ServiceRegistry serviceRegistry=new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer=new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }

}
