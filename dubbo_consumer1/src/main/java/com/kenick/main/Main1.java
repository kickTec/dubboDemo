package com.kenick.main;

import com.kenick.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.locks.ReentrantLock;

public class Main1 {
    public static void main(String[] args) {
        try {
            // spring环境初始化
            System.out.println("开始spring环境初始化!");
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo-consumer-dev.xml"});
            context.start();

            // 启动两个线程调用服务
            ServiceInvokeRunnable serviceInvokeRunnable1 = new ServiceInvokeRunnable(context);
            ServiceInvokeRunnable serviceInvokeRunnable2 = new ServiceInvokeRunnable(context);
            Thread thread1 = new Thread(serviceInvokeRunnable1, "thread1");
            Thread thread2 = new Thread(serviceInvokeRunnable2, "thread2");
            thread1.start();
            thread2.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static class ServiceInvokeRunnable implements Runnable{
        private static Integer invokeTimes = 2000; // 总调用次数
        private ClassPathXmlApplicationContext context; // spring上下文
        private static ReentrantLock locker = new ReentrantLock(); // 锁住调用次数
        private Integer provider1Sum = 0; // 调用服务提供者1次数
        private Integer provider2Sum = 0;// 调用服务提供者2次数

        public ServiceInvokeRunnable(ClassPathXmlApplicationContext context){
            this.context = context;
        }

        public void run() {
            // 多线程处理调用次数
            while(invokeTimes >0){
                locker.lock();
                try {
                    if(invokeTimes>0){
                        invokeTimes--;
                    }else{
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    locker.unlock();
                }

                // 调用提供的服务hello
                String threadName = Thread.currentThread().getName();
                HelloService helloService = context.getBean("helloService", HelloService.class);
                String ret = helloService.hello("kenick-consumer");

                // 统计调用次数
                if(ret.contains("provider1")){
                    provider1Sum++;
                }else if(ret.contains("provider2")){
                    provider2Sum++;
                }
                System.out.println(threadName+":"+"provider1Sum:"+provider1Sum+","+provider1Sum*1.0/(provider1Sum+provider2Sum));
                System.out.println(threadName+":"+"provider2Sum:"+provider2Sum+","+provider2Sum*1.0/(provider1Sum+provider2Sum));
            }
        }
    }
}
