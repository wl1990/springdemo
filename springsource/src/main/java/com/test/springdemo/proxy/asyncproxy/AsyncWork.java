package com.test.springdemo.proxy.asyncproxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AsyncWork {
    private Method method;
    private Object object;
    private Object[] params;
    public AsyncWork(Object handlerObject, Method method, Object[] paramsValues){
        this.object=handlerObject;
        this.method=method;
        this.params=paramsValues;
    }

    public void callback() throws InvocationTargetException, IllegalAccessException {
        method.invoke(object,params);
    }
}
