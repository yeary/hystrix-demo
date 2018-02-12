package com.demo.command.test;

import com.demo.command.HelloWorldHystrixCommand;
import com.netflix.hystrix.HystrixCommand;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by yangyang115 on 18-2-12.
 */
public class HelloWorldHystrixCommandTest {

    @Test
    public void excuteTest() {
        String res = new HelloWorldHystrixCommand("commandTest").execute();
        System.out.println(res);
    }

    @Test
    public void queueTest() throws ExecutionException, InterruptedException {
        HystrixCommand<String> command = new HelloWorldHystrixCommand("commandTest");
        Future<String> future = command.queue();
        String res = future.get();
        System.out.println(res);
    }

    @Test
    public void observeTest() throws InterruptedException {
        Observable<String> fWorld = new HelloWorldHystrixCommand("World").observe();
        Observable<String> fBob = new HelloWorldHystrixCommand("Bob").observe();

        System.out.println(fWorld.toBlocking().single());
        System.out.println(fBob.toBlocking().single());

        fWorld.subscribe(new Observer<String>() {
            public void onCompleted() {
                System.out.println("fWorld onCompleted");
            }

            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            public void onNext(String s) {
                System.out.println("fWorld onNext " + s);
            }
        });

        fBob.subscribe(new Action1<String>() {
            public void call(String s) {
                System.out.println("fBob call " + s);
            }
        });
    }

}
