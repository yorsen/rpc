package com.mine.rpcmine.server;

import com.mine.netty.EchoServer;
import com.mine.rpcmine.codec.HessianCodec;
import com.mine.rpcmine.codec.HessionDecoder;
import com.mine.rpcmine.codec.HessionEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by yaosen.pys on 2017/2/28.
 */
public class RpcServerService {
    private int port;

    public RpcServerService(int port) {
        this.port = port;
    }

    public void start() {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap boostrap = new ServerBootstrap();
            boostrap.group(group).channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HessionDecoder(HessianCodec.getInstace()))
                            .addLast(new HessionEncoder(HessianCodec.getInstace()))
                            .addLast(new MessageReceiveHandler());
                    }
                });

            ChannelFuture future = boostrap.bind().sync();
            System.out.print(EchoServer.class.getName() + " stand and listen on"
                             + future.channel().localAddress());
        } catch (Exception e) {

        }
    }
}
