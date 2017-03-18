package com.mine.rpcmine.client;

import com.mine.rpcmine.model.RequestModel;
import com.mine.rpcmine.model.ResponseModel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yaosen.pys on 2017/2/27.
 */
public class Messagehandler extends ChannelInboundHandlerAdapter {
    private ConcurrentHashMap<String, MessageCallback> callBackMap = new ConcurrentHashMap<>();
    private ChannelHandlerContext                      ctx;

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //        RequestModel request = new RequestModel();
        //        request.setMessageId("1");
        //        request.setClassName("com.mine.rpcmine.TestMethod");
        //        request.setMethodName("testMethod");
        //        Class<?>[] type = new Class[1];
        //        type[0] = String.class;
        //        request.setTypeParameters(type);
        //        Object[] val = new Object[1];
        //        val[0] = "Hello World!";
        //        request.setParametersVal(val);

        this.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ResponseModel responseModel = (ResponseModel) msg;

        if (callBackMap.containsKey(responseModel.getMessageId())) {
            MessageCallback callback = callBackMap.get(responseModel.getMessageId());
            callBackMap.remove(responseModel.getMessageId());
            callback.finish(responseModel);
        }

        //        System.out.print("time=" + System.currentTimeMillis() + " result="
        //                         + JSON.toJSONString(responseModel.getResult()));
    }

    public MessageCallback sendRequest(RequestModel model) {
        MessageCallback callback = new MessageCallback();
        callback.setMessageId(model.getMessageId());
        callback.setRequest(model);

        ctx.channel().writeAndFlush(callback.getResponse());
        callBackMap.put(callback.getMessageId(), callback);

        return callback;
    }
}
