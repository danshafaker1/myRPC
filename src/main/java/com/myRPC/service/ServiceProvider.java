package com.myRPC.service;

public interface ServiceProvider {

    <T> void addServiceProvider(T service);
    Object getService(String serviceName);


}
