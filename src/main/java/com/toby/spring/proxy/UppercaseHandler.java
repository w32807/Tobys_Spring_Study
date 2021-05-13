package com.toby.spring.proxy;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler{
    Object target;
    
    public UppercaseHandler(Object target) {
        this.target = target;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] arg2) throws Throwable {
        Object ret = method.invoke(target, arg2);
        return (ret instanceof String) ? ((String)ret).toUpperCase() : ret; 
    }
}
