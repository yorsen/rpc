package com.mine.rpcmine.codec;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by yaosen.pys on 2017/2/28.
 */
public interface MessageCodecInterface {
    /**
     * 编码
     *
     * @param out
     * @param message
     * @throws IOException
     */
    public void encode(final ByteBuf out, final Object message) throws IOException;

    /**
     * 解码
     *
     * @param body
     * @return
     * @throws IOException
     */
    public Object decode(byte[] body) throws IOException;
}
