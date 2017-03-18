package com.mine.rpcmine.server;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.mine.rpcmine.ExecutorFactory;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by yaosen.pys on 2017/3/2.
 */
public class ParallelProcessContainer {

    private static ListeningExecutorService threadPoolExecutor = ExecutorFactory.getInstance()
        .getExecutorService();

    public static void run(ChannelHandlerContext ctx, ServerReceiveTask task) {
        ListenableFuture<Boolean> taskListener = threadPoolExecutor.submit(task);

        //保证了顺序执行
        Futures.addCallback(taskListener, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                ctx.writeAndFlush(task.getResponse());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                ctx.close();
            }
        }, threadPoolExecutor);
    }
}
