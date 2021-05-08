package com.myRPC.Rpc_Center;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcResponse<T> implements Serializable {

    /**
     * 相应状态码
     */
    private Integer statusCode;

    /**
     * 相应状态补充信息
     */
    private String msg;

    /**
     * 相应数据
     */
    private T data;

    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> response=new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }
    public static <T> RpcResponse<T> fail(ResponseCode code){
        RpcResponse<T> response=new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMsg(code.getMsg());
        return response;
    }
}
