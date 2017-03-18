package com.mine.rpcmine.model;

import java.io.Serializable;

/**
 * Created by yaosen.pys on 2017/2/27.
 */
public class ResponseModel implements Serializable {
    private static final long serialVersionUID = -6924611719184616844L;
    private String            messageId;
    private String            errorCode;
    private Object            result;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
