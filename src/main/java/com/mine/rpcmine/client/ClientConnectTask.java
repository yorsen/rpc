package com.mine.rpcmine.client;

import com.mine.rpcmine.codec.HessianCodec;
import com.mine.rpcmine.codec.HessionDecoder;
import com.mine.rpcmine.codec.HessionEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yaosen.pys on 2017/3/9.
 */
public class ClientConnectTask implements Callable<Boolean> {
    private String                                    address;
    private int                                       port;
    private EventLoopGroup                            group;
    private ConcurrentHashMap<String, Messagehandler> messageHandlers;
    private ReentrantLock                             lock;

    public ClientConnectTask(String address, int port, EventLoopGroup group,
                             ConcurrentHashMap<String, Messagehandler> messageHandlers) {
        this.address = address;
        this.port = port;
        this.group = group;
        this.messageHandlers = messageHandlers;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(address, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HessionDecoder(HessianCodec.getInstace()))
                            .addLast(new HessionEncoder(HessianCodec.getInstace()));
                        Messagehandler handler = new Messagehandler();
                        messageHandlers.put(address + "_" + String.valueOf(port), handler);
                        ch.pipeline().addLast(new Messagehandler());
                    }
                });
            b.connect();
        } catch (Exception e) {
            return false;
        } finally {
            group.shutdownGracefully().sync();
        }

        return true;
    }
}
