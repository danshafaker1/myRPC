package com.myRPC.enum_util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SerializerCode {

    JSON(1);

    private final int code;

}
