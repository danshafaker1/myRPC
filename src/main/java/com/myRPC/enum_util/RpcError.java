package com.myRPC.enum_util;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RpcError {

    SERVICE_INVOCATION_FAILURE("服务调用出现失败"),
    SERVICE_CAN_NOT_BE_NULL("注册的服务不得为空"),
    SERVICE_NOT_FOUND("找不到对应的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口"),
    UNKNOWN_PROTOCOL("不识别的协议包"),
    UNKNOWN_SERIALIZER("不识别的(反)序列化器"),
    UNKNOWN_PACKAGE_TYPE("不识别的数据包类型"),
    REGISTER_SERVICE_FAILED("注册服务失败"),
    SERIALIZER_NOT_FOUND("没有初始序列化器"),
    SERVICE_SCAN_PACKAGE_NOT_FOUND("未扫描到指定的RPC服务"),
    UNKNOWN_ERROR("未知错误"),
    FAILED_TO_CONNECT_TO_SERVICE_REGISTRY("连接不到服务中心");
    private final String msg;

}
