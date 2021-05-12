package com.myRPC.service.impl;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.myRPC.LoadBalance.Impl.RandomLoadBalancer;
import com.myRPC.LoadBalance.LoadBalancer;
import com.myRPC.enum_util.RpcError;
import com.myRPC.exception.RpcException;
import com.myRPC.service.ServiceRegistry;
import com.myRPC.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

public class BalanceNacosServiceRegistry implements ServiceRegistry {


    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    private static final String SERVER_ADDR="localhost:8848";
    private static final NamingService namingService;


    private final LoadBalancer loadBalancer;

    public BalanceNacosServiceRegistry(LoadBalancer loadBalancer) {
        if(loadBalancer == null) this.loadBalancer = loadBalancer;
        else this.loadBalancer = loadBalancer;
    }


    static {
        try {
            namingService= NamingFactory.createNamingService(SERVER_ADDR);
        }catch (NacosException e){
            logger.error("连接到Nacos时有错误发生： ",e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY.getMsg());
        }
    }



    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            namingService.registerInstance(serviceName,inetSocketAddress.getHostName(),inetSocketAddress.getPort());
            NacosUtil.registerService(serviceName,inetSocketAddress);
        }
        catch (NacosException e){
            logger.error("注册服务时有错误发生:",e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED.getMsg());
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances=namingService.getAllInstances(serviceName);
            Instance instance=loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(),instance.getPort());
        }catch (NacosException e){
            logger.error("获取服务时有错误发生",e);
        }
        return null;
    }
}
