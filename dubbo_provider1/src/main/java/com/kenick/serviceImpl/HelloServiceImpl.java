package com.kenick.serviceImpl;

import com.kenick.service.HelloService;

public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello " + name + " from provider1.";
    }
}
