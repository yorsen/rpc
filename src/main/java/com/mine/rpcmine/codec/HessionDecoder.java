package com.mine.rpcmine.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by yaosen.pys on 2017/2/28.
 */
public class HessionDecoder extends ByteToMessageDecoder {
    private MessageCodecInterface messageCodec;

    public HessionDecoder(MessageCodecInterface messageCodec) {
        this.messageCodec = messageCodec;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in,
                          List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }

        in.markReaderIndex();
        int length = in.readInt();
        if (length <= 0) {
            ctx.close();
        }

        if (length > in.readableBytes()) {
            in.resetReaderIndex();
            return;
        } else {
            byte[] bytes = new byte[length];
            in.readBytes(bytes);
            out.add(messageCodec.decode(bytes));
        }
    }
}
