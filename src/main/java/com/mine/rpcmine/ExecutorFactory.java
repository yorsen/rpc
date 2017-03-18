package com.mine.rpcmine;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yaosen.pys on 2017/3/2.
 */
public class ExecutorFactory {
    private static ListeningExecutorService executorService;

    private ExecutorFactory(int threads, int queueSize) {
        executorService = MoreExecutors.listeningDecorator(new ThreadPoolExecutor(threads, threads,
            10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueSize)));
    }

    private static final class ExecutorFactoryHolder {
        private static ExecutorFactory instance = new ExecutorFactory(20, 300);
    }

    public static ExecutorFactory getInstance() {
        return ExecutorFactoryHolder.instance;
    }

    public ListeningExecutorService getExecutorService() {
        return executorService;
    }
}
