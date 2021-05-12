package com.myRPC.LoadBalance.Impl;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.myRPC.LoadBalance.LoadBalancer;

import java.util.List;
import java.util.Random;

public class RandomLoadBalancer implements LoadBalancer {
    @Override
    public Instance select(List<Instance> intances) {
        return intances.get(new Random().nextInt(intances.size()));
    }
}
