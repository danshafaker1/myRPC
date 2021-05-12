package com.myRPC.Rpc_Center;

import com.myRPC.util.CommonSerializer;

public interface RpcClient {


    Object sendRequest(RpcRequest rpcRequest);
    void setSerializer(CommonSerializer serializer);

}
