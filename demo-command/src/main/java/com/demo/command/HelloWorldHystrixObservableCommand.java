package com.demo.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by yangyang115 on 18-2-12.
 */
public class HelloWorldHystrixObservableCommand extends HystrixObservableCommand<String> {

    private String name;

    private static final Setter cachedSetter =
            Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"));

    public HelloWorldHystrixObservableCommand(String name) {
        //super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        super(cachedSetter);
        this.name = name;
    }


    @Override
    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
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

    @Override
    protected Observable resumeWithFallback() {
        return super.resumeWithFallback();
    }
}
