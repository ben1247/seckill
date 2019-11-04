package org.seckill.hystrix;

import com.netflix.hystrix.*;

public class HystrixHelloWorld extends HystrixCommand<String> {

    private String name;

    public HystrixHelloWorld(String name){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(name))
                .andCommandKey(HystrixCommandKey.Factory.asKey(name+"query"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(name+"ThreadPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(2))//服务线程池数量
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withCircuitBreakerErrorThresholdPercentage(60)//熔断器关闭到打开阈值
                        .withCircuitBreakerSleepWindowInMilliseconds(3000)//熔断器打开到关闭的时间窗长度
        ));

        this.name = name;
    }


    @Override
    protected String run() throws Exception {
        return "==========================Hello " + name + "! ==========================";
    }

    @Override
    protected String getFallback() {
        return "==========================Sorry " + name + "! ==========================";
    }

    public static void main(String [] args) throws Exception{


        new Thread(() -> {
            for (int i = 0 ; i <= 100; i++){
                String es = new HystrixHelloWorld("ben").execute();
                System.out.println(es);
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            for (int i = 0 ; i <= 100; i++){
                String es = new HystrixHelloWorld("yeye").execute();
                System.out.println(es);
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0 ; i <= 100; i++){
                String es = new HystrixHelloWorld("nana").execute();
                System.out.println(es);
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0 ; i <= 100; i++){
                String es = new HystrixHelloWorld("nan").execute();
                System.out.println(es);
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
