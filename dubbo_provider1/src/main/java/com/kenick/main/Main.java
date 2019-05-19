package com.kenick.main;

import com.kenick.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        // spring环境初始化,同时通过spring将服务注册到zookeeper上
        System.out.println("spring环境初始化!");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo-provider-dev.xml"});
        context.start();

        try {
            // 测试服务注入到spring中是否成功
            HelloService helloService = context.getBean("helloService", HelloService.class);
            String ret = helloService.hello("kenick-provider");
            System.out.println(ret);

            System.in.read(); // 程序等待输入卡住
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
