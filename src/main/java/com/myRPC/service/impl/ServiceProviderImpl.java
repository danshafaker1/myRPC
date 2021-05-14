package com.myRPC.service.impl;

import com.myRPC.enum_util.RpcError;
import com.myRPC.exception.RpcException;
import com.myRPC.service.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceProviderImpl implements ServiceProvider {

    private static final Logger logger= LoggerFactory.getLogger(ServiceProviderImpl.class);

    private static final Map<String,Object> serviceMap=new ConcurrentHashMap<>();
    private static Set<String> registeredService=ConcurrentHashMap.newKeySet();



    @Override
    public synchronized <T> void addServiceProvider(T service,String serviceName) {
        if(registeredService.add(serviceName)==false) return;
        //获得所有接口
        Class<?>[] interfaces=service.getClass().getInterfaces();
        //抛出异常
        if(interfaces.length==0) throw new RpcException(RpcError.SERVICE_CAN_NOT_BE_NULL.getMsg());
        for(Class<?> i:interfaces){
            serviceMap.put(i.getCanonicalName(),service);
        }
        logger.info("向接口 ：{} 注册服务：{}",interfaces,serviceName);
    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service==null){
            throw new RpcException(RpcError.SERVICE_NOT_FOUND.getMsg());
        }
        return service;
    }
}
