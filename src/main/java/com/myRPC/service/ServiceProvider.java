package com.myRPC.service;

public interface ServiceProvider {

    <T> void addServiceProvider(T service,String serviceName);
    Object getService(String serviceName);


}
