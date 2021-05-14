package com;

import com.myRPC.Netty_Center.NettyServer;
import com.myRPC.annotion.ServiceScan;
import com.myRPC.enum_util.SerializerCode;
import com.myRPC.service.HelloService;
import com.myRPC.service.ServiceProvider;
import com.myRPC.service.impl.ServiceProviderImpl;
import com.myRPC.service.impl.HelloServiceImpl;
import com.myRPC.util.KryoSerializer;
import com.myRPC.util.NacosUtil;

import java.net.InetSocketAddress;
import java.util.Scanner;


@ServiceScan("com.myRPC.service.impl")
public class TestNettyServer {

    public static void main(String[] args) {

        NettyServer server=new NettyServer("localhost",9999, SerializerCode.KRYO.getCode());
        server.start();
    }


}
