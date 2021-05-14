package com.myRPC.Rpc_Center;

import com.myRPC.util.CommonSerializer;

public interface RpcServer {

    void start();

    <T> void publishService(T service,String serviceName);

    void setSerializer(CommonSerializer serializer);

}
