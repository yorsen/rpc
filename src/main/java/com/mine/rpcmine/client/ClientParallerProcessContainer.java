package com.mine.rpcmine.client;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.mine.rpcmine.ExecutorFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yaosen.pys on 2017/3/8.
 */
public class ClientParallerProcessContainer {
    private static ListeningExecutorService           threadPoolExecutor = ExecutorFactory
        .getInstance().getExecutorService();

    private Messagehandler                            channelHandler;
    private EventLoopGroup                            group              = new NioEventLoopGroup();

    private ConcurrentHashMap<String, Messagehandler> messageHandlers;

    private ReentrantLock                             lock               = new ReentrantLock();
    private Condition                                 taskCondition      = lock.newCondition();

    public ClientParallerProcessContainer(ConcurrentHashMap<String, Messagehandler> messageHandlers) {

        this.messageHandlers = messageHandlers;
    }

    public void run(String address, int port) {
        ClientConnectTask task = new ClientConnectTask(address, port, group, messageHandlers);
        threadPoolExecutor.submit(task);
    }

    public Messagehandler getMessageHandler(String address, int port) {
        String key = address + "_" + String.valueOf(port);
        if (messageHandlers.containsKey(key)) {
            try {
                lock.wait(10);
            } catch (InterruptedException e) {

            }
        }

        return messageHandlers.get(key);
    }
}
