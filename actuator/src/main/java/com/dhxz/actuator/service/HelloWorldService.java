package com.dhxz.actuator.service;

import com.dhxz.actuator.config.ServiceProperties;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    private final ServiceProperties serviceProperties;

    public HelloWorldService(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    public String getHelloMessage() {
        return "Hello " + this.serviceProperties.getName();
    }
}
