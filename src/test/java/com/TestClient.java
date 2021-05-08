package com;

import com.myRPC.Rpc_Center.RpcClientProxy;
import com.myRPC.pojo.HelloObject;
import com.myRPC.service.HelloService;

public class TestClient {

    public static void main(String[] args) {
        RpcClientProxy proxy=new RpcClientProxy("localhost",9000);
        HelloService helloService=proxy.getProxy(HelloService.class);
        HelloObject object=new HelloObject(10,"This is msg.");
        String res= helloService.hello(object);
        System.out.println(res);

    }

}
