package com;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRPC.Rpc_Center.RpcRequest;
import com.myRPC.service.HelloService;
import com.myRPC.service.impl.HelloServiceImpl;

public class JsonTest {


    public static void main(String[] args) throws JsonProcessingException {

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("程序已退出。。。");
        }));


        while (true){

        }
    }

}
