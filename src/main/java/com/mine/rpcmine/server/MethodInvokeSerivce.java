package com.mine.rpcmine.server;

import com.mine.rpcmine.model.RequestModel;
import com.mine.rpcmine.model.ResponseModel;
import org.apache.commons.lang3.reflect.MethodUtils;

/**
 * Created by yaosen.pys on 2017/3/2.
 */
public class MethodInvokeSerivce {
    public static Object invoke(RequestModel request) {
        String methodName = request.getMethodName();
        Object[] parameters = request.getParametersVal();

        ResponseModel result = new ResponseModel();
        Object calculateResult = null;

        try {
            Class<?> c = Class.forName(request.getClassName());
            calculateResult = MethodUtils.invokeMethod(c.newInstance(), methodName, parameters);
        } catch (Exception e) {
        }

        if (null == calculateResult) {
            result.setErrorCode("1");
        }

        result.setErrorCode("0");
        result.setMessageId(request.getMessageId());
        result.setResult(calculateResult);

        return result;
    }
}
