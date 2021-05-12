package com.myRPC.LoadBalance.Impl;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.myRPC.LoadBalance.LoadBalancer;

import java.util.List;

public class RoundRobinLoadBalancer implements LoadBalancer {

    private int index=0;


    @Override
    public Instance select(List<Instance> intances) {
        if(index>=intances.size()){
            index%=intances.size();

        }
        return intances.get(index++);
    }
}
