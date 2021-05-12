package com.myRPC.Rpc_Center;

import com.myRPC.util.CommonSerializer;

public interface RpcServer {

    void start();

    <T> void publishService(Object service,Class<T> serviceClass);

    void setSerializer(CommonSerializer serializer);

}
