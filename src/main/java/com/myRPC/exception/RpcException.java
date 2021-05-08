package com.myRPC.exception;


import lombok.AllArgsConstructor;


public class RpcException extends RuntimeException {

    public RpcException(String message) {
        super(message);
    }
}
