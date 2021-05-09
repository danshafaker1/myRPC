package com.myRPC.Rpc_Center;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {

    /**
     * 待调用接口的名字
     */

    private String interfaceName;

    /**
     *待调用方法名称
     */

    private String methodName;

    /**
     * 调用方法的参数类型
     */

    private Object[] parameters;

    /**
     *
     * 调用方法的参数类型
     */
    private Class<?>[] paramTypes;
}
