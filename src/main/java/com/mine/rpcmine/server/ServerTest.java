package com.mine.rpcmine.server;

import com.mine.rpcmine.model.RequestModel;

/**
 * Created by yaosen.pys on 2017/2/28.
 */
public class ServerTest {
    public static void main(String[] args) {
        new RpcServerService(8080).start();
    }




    public void testInvoke(String[] args) {
        RequestModel request = new RequestModel();
        request.setMessageId("1");
        request.setClassName("com.mine.rpcmine.TestMethod");
        request.setMethodName("testMethod");
        Class<?>[] type = new Class[1];
        type[0] = String.class;
        request.setTypeParameters(type);
        Object[] val = new Object[1];
        val[0] = "Hello World!";
        request.setParametersVal(val);

        MessageReceiveHandler handler = new MessageReceiveHandler();
    }
}
