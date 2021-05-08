package com.myRPC.service;

public interface ServiceRegistry {

    <T> void register(T service);
    Object getService(String serviceName);


}
