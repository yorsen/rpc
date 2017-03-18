package com.mine.rpcmine.client;

import com.mine.rpcmine.model.RequestModel;
import com.mine.rpcmine.model.ResponseModel;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yaosen.pys on 2017/3/8.
 */
public class MessageCallback {
    private String        messageId;
    private RequestModel  request;
    private ResponseModel response;

    private ReentrantLock lock   = new ReentrantLock();

    private Condition     finish = lock.newCondition();

    public Object get(Long milliSeconds) throws Exception {
        try {
            lock.lock();
            if (null != response) {
                return response.getResult();
            }

            if (null == milliSeconds || milliSeconds < 0) {
                finish.wait();
            } else {
                finish.await(milliSeconds, TimeUnit.MILLISECONDS);
            }

            if (null == response) {
                throw new RuntimeException();
            }

            return response.getResult();
        } finally {
            lock.unlock();
        }
    }

    public void finish(ResponseModel response) {
        try {
            lock.lock();
            finish.signalAll();
            this.response = response;
        } finally {
            lock.unlock();
        }
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public RequestModel getRequest() {
        return request;
    }

    public void setRequest(RequestModel request) {
        this.request = request;
    }

    public ResponseModel getResponse() {
        return response;
    }

    public void setResponse(ResponseModel response) {
        this.response = response;
    }
}
