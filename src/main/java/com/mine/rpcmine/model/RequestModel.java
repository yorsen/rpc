package com.mine.rpcmine.model;

import java.io.Serializable;

/**
 * Created by yaosen.pys on 2017/2/27.
 */
public class RequestModel implements Serializable {
    private static final long serialVersionUID = -6046942688850611695L;
    private String     messageId;
    //类名
    private String     className;

    //方法名
    private String     methodName;

    //参数类型
    private Class<?>[] typeParameters;

    //参数名
    private Object[]   parametersVal;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(Class<?>[] typeParameters) {
        this.typeParameters = typeParameters;
    }

    public Object[] getParametersVal() {
        return parametersVal;
    }

    public void setParametersVal(Object[] parametersVal) {
        this.parametersVal = parametersVal;
    }
}
