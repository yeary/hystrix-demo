package com.demo.command.test;

import com.demo.command.HelloWorldHystrixObservableCommand;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by yangyang115 on 18-2-12.
 */
public class HelloWorldHystrixObservableCommandTest {

    @Test
    public void observeTest(){
        Observable<String> fWorld = new HelloWorldHystrixObservableCommand("World").observe();
        Observable<String> fBob = new HelloWorldHystrixObservableCommand("Bob").observe();



        fWorld.subscribe(new Observer<String>() {
            public void onCompleted() {
                System.out.println("fWorld onCompleted!");
            }

            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            public void onNext(String s) {
                System.out.println("fWorld onNext: " + s);
            }
        });

        fBob.subscribe(new Action1<String>() {
            public void call(String s) {
                System.out.println("fBob call: " + s);
            }
        });

    }
}
