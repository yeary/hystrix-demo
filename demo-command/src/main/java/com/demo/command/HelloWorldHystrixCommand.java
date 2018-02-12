package com.demo.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloWorldHystrixCommand extends HystrixCommand<String> {

    private final String name;

    public HelloWorldHystrixCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    // 如果继承的是HystrixObservableCommand，要重写Observable construct() 
    @Override
    protected String run() {
        return "Hello " + name;
    }

    @Override
    protected String getFallback() {
        return name+ ": this request is error";
    }
}
