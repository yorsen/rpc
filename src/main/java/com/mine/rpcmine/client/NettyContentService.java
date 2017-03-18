package com.mine.rpcmine.client;

import com.mine.rpcmine.codec.HessianCodec;
import com.mine.rpcmine.codec.HessionDecoder;
import com.mine.rpcmine.codec.HessionEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class NettyContentService {
    private String address;
    private int    port;
    private int    times;

    public NettyContentService(String address, int port, int times) {
        this.address = address;
        this.port = port;
        this.times = times;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(address, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HessionDecoder(HessianCodec.getInstace()))
                            .addLast(new HessionEncoder(HessianCodec.getInstace()))
                            .addLast(new Messagehandler());
                    }
                });
            ChannelFuture future = b.connect().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
