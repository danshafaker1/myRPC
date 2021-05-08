package com.myRPC.Rpc_Center;

import com.myRPC.enum_util.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public Object handle(RpcRequest rpcRequest,Object service){
        Object result=null;
        try {
            result=invokeTargetMethod(rpcRequest,service);
        } catch (IllegalAccessException | InvocationTargetException e){
            logger.error("调用或发送时有错误发生: ",e);
        }
        return result;
    }
    private Object invokeTargetMethod(RpcRequest rpcRequest,Object service) throws InvocationTargetException, IllegalAccessException {

        Method method;
        try {
            //通过反射获取Client端调用的方法
            method=service.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParamTypes());
        }catch (NoSuchMethodException e){
            return RpcResponse.fail(ResponseCode.NOT_FOUND_METHOD);
        }
        return method.invoke(service,rpcRequest.getParameters());
    }


}
