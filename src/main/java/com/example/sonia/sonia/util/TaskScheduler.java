package com.example.sonia.sonia.util;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class TaskScheduler {


    private static final int THREAD_POOL_SIZE = 1;

    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);


    private TaskScheduler() {
    }

    public static void scheduleOnce(Runnable task, long delay, TimeUnit unit) {
        executor.schedule(task, delay, unit);
    }

    public static void schedule(Runnable task, long initialDelay, long updateInterval,
                                TimeUnit timeUnit) {
        executor.scheduleWithFixedDelay(task, initialDelay, updateInterval, timeUnit);
    }

    public static void schedule(Runnable task, long updateInterval, TimeUnit timeUnit) {
        executor.scheduleWithFixedDelay(task, 0, updateInterval, timeUnit);
    }

}
