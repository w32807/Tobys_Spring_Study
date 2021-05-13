package com.toby.spring.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionHandler implements InvocationHandler{
    private Object target;
    private PlatformTransactionManager transactionManager;
    private String pattern; // Ʈ������� ������ �޼ҵ� �̸� ����
    
    
    public void setTarget(Object target) {
        this.target = target;
    }


    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }


    public void setPattern(String pattern) {
        this.pattern = pattern;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return (method.getName().startsWith(pattern)) ? invokeInTransaction(method, args) : method.invoke(method, args);  
    }


    private Object invokeInTransaction(Method method, Object[] args) throws Throwable {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object ret = method.invoke(target, args);
            this.transactionManager.commit(status);
            return ret;
        } catch (InvocationTargetException e) {
            this.transactionManager.rollback(status);
            throw e.getTargetException();
        }
    }
}
