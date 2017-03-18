package com.mine.rpcmine.client;

import com.mine.rpcmine.model.RequestModel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yaosen.pys on 2017/2/28.
 */
public class ClientTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Messagehandler> messageHandler = new ConcurrentHashMap<>();

        ClientParallerProcessContainer container = new ClientParallerProcessContainer(
            messageHandler);

        ExecutorService executor = Executors.newFixedThreadPool(1000);

        try {
            long startTime = System.currentTimeMillis();
            System.out.println("StartTime=" + startTime);
            for (int i = 0; i < 1000; i++) {
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            container.run("127.0.0.1", 8080);
                            Messagehandler handler = container.getMessageHandler("127.0.0.1", 8080);
                            MessageCallback callback = handler.sendRequest(buildRequestModel());

                            System.out.print(callback.getResponse().getResult());
                            // new NettyContentService("127.0.0.1", 8080, 1).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {

        }
    }

    private static RequestModel buildRequestModel() {
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

        return request;
    }
}
