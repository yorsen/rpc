package com.mine.rpcmine.codec;

import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianCodec implements MessageCodecInterface {
    private HessianCodec() {

    }

    private static final class HessianCodecHolder {
        public static HessianCodec Instance = new HessianCodec();
    }

    public static HessianCodec getInstace() {
        return HessianCodecHolder.Instance;
    }

    @Override
    public void encode(ByteBuf out, Object message) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            HessianSerialize hessianSerialize = HessianSerialize.getInstance();
            hessianSerialize.serialize(byteArrayOutputStream, message);
            byte[] body = byteArrayOutputStream.toByteArray();
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
            byteArrayOutputStream.close();
        } catch (Exception e) {

        }
    }

    @Override
    public Object decode(byte[] body) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
            HessianSerialize hessianSerialize = HessianSerialize.getInstance();
            return hessianSerialize.deserialize(inputStream);
        } catch (Exception e) {

        }

        return null;
    }
}
