package com.mine.rpcmine.server;

import com.mine.rpcmine.model.RequestModel;
import com.mine.rpcmine.model.ResponseModel;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yaosen.pys on 2017/3/2.
 */
public class ServerReceiveTask implements Callable<Boolean> {
    private ResponseModel response;
    private RequestModel  request;

    private ReentrantLock lock   = new ReentrantLock();
    private Condition     finish = lock.newCondition();

    public ServerReceiveTask(ResponseModel response, RequestModel request) {
        this.request = request;
        this.response = response;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            response = (ResponseModel) MethodInvokeSerivce.invoke(request);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResponseModel getResponse() {
        return response;
    }
}
