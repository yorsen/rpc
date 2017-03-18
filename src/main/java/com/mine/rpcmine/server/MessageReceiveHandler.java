package com.mine.rpcmine.server;

import com.mine.rpcmine.model.RequestModel;
import com.mine.rpcmine.model.ResponseModel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by yaosen.pys on 2017/2/28.
 */
public class MessageReceiveHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            RequestModel request = (RequestModel) msg;
            ParallelProcessContainer.run(ctx, new ServerReceiveTask(new ResponseModel(), request));
            //ctx.writeAndFlush(MethodInvokeSerivce.invoke(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
