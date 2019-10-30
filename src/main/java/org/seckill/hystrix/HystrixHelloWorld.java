package org.seckill.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;

import java.util.concurrent.Future;

public class HystrixHelloWorld extends HystrixCommand<String> {

    private String name;

    public HystrixHelloWorld(String name){
        super(HystrixCommandGroupKey.Factory.asKey(name));
        this.name = name;
    }


    @Override
    protected String run() throws Exception {
        return "Hello " + name + "!";
    }

    public static void main(String [] args) throws Exception{
        String es = new HystrixHelloWorld("ben").execute();
        System.out.println(es);

        Future<String> fs = new HystrixHelloWorld("yeye").queue();
        System.out.println(fs.get());

        Observable<String> os = new HystrixHelloWorld("nana").observe();
    }
}
