package com.kenick.main;

import com.kenick.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        // spring环境初始化
        System.out.println("spring环境初始化!");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo-provider-dev.xml"});
        context.start();

        try {
            // 测试服务注入到spring中是否成功
            HelloService helloService = context.getBean("helloService", HelloService.class);
            String ret = helloService.hello("kenick-provider2");
            System.out.println(ret);

            System.in.read(); // 等待输入，先卡主
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
