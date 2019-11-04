package org.seckill.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class HystrixObservableHelloWorld extends HystrixObservableCommand<String> {

    private final String name;

    public HystrixObservableHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey(name));
        this.name = name;
    }


    @Override
    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        // a real example would do work like a network call here
                        subscriber.onNext("Hello");
                        subscriber.onNext(name + "!");
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public static void main(String [] args){
        Observable<String> str = new HystrixObservableHelloWorld("World").construct();
        System.out.println(str.toString());
    }
}
