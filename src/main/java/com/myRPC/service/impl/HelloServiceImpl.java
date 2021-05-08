package com.myRPC.service.impl;

import com.myRPC.pojo.HelloObject;
import com.myRPC.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HelloServiceImpl implements HelloService {

    private static final Logger logger= LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到："+object.getMsg());
        return "这是调用的返回值,id="+object.getId().toString();
    }
}
