package com;

import com.myRPC.Netty_Center.NettyClient;
import com.myRPC.Netty_Center.NettyServer;
import com.myRPC.Rpc_Center.RpcClient;
import com.myRPC.Rpc_Center.RpcClientProxy;
import com.myRPC.pojo.HelloObject;
import com.myRPC.service.HelloService;
import com.myRPC.util.KryoSerializer;

public class TestNettyClient {




    public static void main(String[] args) {


        //客户端启动生成代理对象
        RpcClient client=new NettyClient();
        client.setSerializer(new KryoSerializer());
        RpcClientProxy rpcClientProxy=new RpcClientProxy(client);


        HelloService helloService=rpcClientProxy.getProxy(HelloService.class);
        HelloObject object=new HelloObject(12,"This is a msg");
        String res = helloService.hello(object);
        System.out.println(res);


    }


}
