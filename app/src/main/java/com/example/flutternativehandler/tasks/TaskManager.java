package com.example.flutternativehandler.tasks;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    private final BlockingQueue<Runnable> WorkQueue;
    private final ThreadPoolExecutor threadPoolExecutor;

    public TaskManager(int corePoolSize, int maxPoolSize, int keepAliveTime) {
        WorkQueue = new LinkedBlockingQueue<Runnable>();
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime,
                TimeUnit.MILLISECONDS, WorkQueue);
    }

    public void runTask(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    public void killAll() {
        threadPoolExecutor.shutdownNow();
    }
}
