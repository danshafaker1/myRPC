package com;

import com.myRPC.Rpc_Center.RpcServer;
import com.myRPC.service.HelloService;
import com.myRPC.service.impl.HelloServiceImpl;

public class TestServer {

    public static void main(String[] args) {

        //注册HelloService
        HelloService helloService=new HelloServiceImpl();
        RpcServer rpcServer=new RpcServer();
        rpcServer.register(helloService,9000);
    }

}
