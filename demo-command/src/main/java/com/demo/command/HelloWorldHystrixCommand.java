package com.demo.command;

import com.netflix.hystrix.*;

public class HelloWorldHystrixCommand extends HystrixCommand<String> {

    private final String name;

    private static final HystrixCommand.Setter cachedSetter =
            HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldTest"))
                    .andThreadPoolPropertiesDefaults(    // 配置线程池
                            HystrixThreadPoolProperties.Setter()
                                    .withCoreSize(200)    // 配置线程池里的线程数，设置足够多线程，以防未熔断却打满threadpool
                    )
                    .andCommandPropertiesDefaults(    // 配置熔断器
                            HystrixCommandProperties.Setter()
                                    .withCircuitBreakerEnabled(true)
                                    .withCircuitBreakerRequestVolumeThreshold(3)
                                    .withCircuitBreakerErrorThresholdPercentage(80));
//                      .withCircuitBreakerForceOpen(true)	// 置为true时，所有请求都将被拒绝，直接到fallback
//                		.withCircuitBreakerForceClosed(true)	// 置为true时，将忽略错误
//                		.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)	// 信号量隔离
//                		.withExecutionTimeoutInMilliseconds(5000)

    public HelloWorldHystrixCommand(String name) {
        //super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        super(cachedSetter);
        this.name = name;
    }

    // 如果继承的是HystrixObservableCommand，要重写Observable construct() 
    @Override
    protected String run() {
        return "Hello " + name;
        //throw new RuntimeException("this is a error");
    }

    @Override
    protected String getFallback() {
        this.getExecutionException().printStackTrace();
        return name + ": this request is error";
    }
}
