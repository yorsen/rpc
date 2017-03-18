package com.mine.rpcmine.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by yaosen.pys on 2017/2/28.
 */
public class HessionEncoder extends MessageToByteEncoder {
    private MessageCodecInterface messageCodec;

    public HessionEncoder(MessageCodecInterface messageCodec) {
        this.messageCodec = messageCodec;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
//        if (msg instanceof EmptyByteBuf) {
//            return;
//        }
        messageCodec.encode(out, msg);
    }
}
