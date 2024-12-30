package com.yupi.springbootinit;


import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.ehcache.shadow.org.terracotta.statistics.Time;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RxJavaTest {
    @Test
    public void test() throws InterruptedException{
        //创建数据流
        Flowable<Long> flowable=Flowable.interval(1, TimeUnit.SECONDS)
                .map(i->i+1)
                .subscribeOn(Schedulers.io()); //执行执行操作用的线程池
        //订阅Flowable
        flowable
                .observeOn(Schedulers.io())
                .doOnNext(item-> System.out.println(item.toString()))
                .subscribe();
        //主线程睡眠,已方便观察结果
        Thread.sleep(10000L);
    }
}
