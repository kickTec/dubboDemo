package com.kenick.serviceImpl;

import com.kenick.service.HelloService;

public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "hello "+name+" from provider2.";
    }
}
