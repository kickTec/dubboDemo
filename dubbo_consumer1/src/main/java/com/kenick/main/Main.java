package com.kenick.main;

import com.kenick.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        try {
            // spring环境初始化
            System.out.println("开始spring环境初始化!");
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo-consumer-dev.xml"});
            context.start();

            int provider1Sum = 0;
            int provider2Sum = 0;
            for(int i=0;i<200;i++){
                // 开始通过spring和dubbo获取远程服务实例
                HelloService helloService = context.getBean("helloService", HelloService.class);

                // 调用提供的服务hello
                String ret = helloService.hello("kenick-consumer");
                System.out.println(ret);

                // 统计调用次数
                if(ret.contains("provider1")){
                    provider1Sum += 1;
                }else if(ret.contains("provider2")){
                    provider2Sum += 1;
                }
                System.out.println("provider1Sum:"+provider1Sum+","+provider1Sum*1.0/(provider1Sum+provider2Sum));
                System.out.println("provider2Sum:"+provider2Sum+","+provider2Sum*1.0/(provider1Sum+provider2Sum));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
