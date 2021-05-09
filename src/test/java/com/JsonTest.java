package com;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRPC.Rpc_Center.RpcRequest;

public class JsonTest {

    private static ObjectMapper objectMapper=new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        String str="{\"interfaceName\":\"com.myRPC.service.HelloService\",\"methodName\":\"hello\",\"parameters\":[{\"id\":12,\"msg\":\"This is a msg\"}],\"paramTypes\":[\"com.myRPC.pojo.HelloObject\"]}\n";
        RpcRequest rpcRequest = objectMapper.readValue(str, RpcRequest.class);
        System.out.println(rpcRequest);
    }

}
