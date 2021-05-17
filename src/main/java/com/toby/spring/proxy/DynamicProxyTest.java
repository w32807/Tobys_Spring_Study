package com.toby.spring.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;


public class DynamicProxyTest {
    
    @Test
    // Dynamic proxy ���� (���� ����)
    public void simpleProxy() {
        Hello proxiedHello = (Hello)Proxy.newProxyInstance(
                                getClass().getClassLoader(), 
                                new Class[] {Hello.class}
                                , new UppercaseHandler(new HelloTarget()));
    }
    
    @Test
    // Dynamic proxy ���� (FactoryBean ���)
    // advice�� Ÿ�꿡 �������� �ʰ� ������ �ΰ�������ν� �۵��Ѵ�.
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());// Ÿ�� ���� (Ÿ�� ������Ʈ�� �����ϰ� �ִ� �������̽��� ProxyFactoryBean���� ã�ư���)
        pfBean.addAdvice(new UppercaseAdvice());// �ΰ������ ���� �����̽� �߰� (���� ���� ����)

        Hello proxiedHello = (Hello)pfBean.getObject(); // getObject�� ������ ���Ͻø� ������
        assertEquals(proxiedHello.sayHello("����"), "HELLO ����");
        assertEquals(proxiedHello.sayHi("����"), "HI ����");
        assertEquals(proxiedHello.sayThankYou("����"), "THANKYOU ����");
    }
    
    static class UppercaseAdvice implements MethodInterceptor{
        public Object invoke(MethodInvocation invocation) throws Throwable{
            String ret = (String)invocation.proceed(); // ���÷����� Method�� �޸� �޼ҵ� ���� �� Ÿ�� ������Ʈ�� ������ �ʿ䰡 ����
                                                       // MethodInvocation�� �޼ҵ� ������ �Բ� Ÿ�� ������Ʈ�� �˰� �ֱ� �����̴�.
            return ret.toUpperCase();
        }
    }
    
    static interface Hello{
        String sayHello(String name);
        String sayHi(String name);
        String sayThankYou(String name);
    }
    
    static class HelloTarget implements Hello{
        
        @Override
        public String sayHello(String name) {
            return "Hello " + name;
        }
        
        @Override
        public String sayHi(String name) {
            return "Hi " + name;
        }
        
        @Override
        public String sayThankYou(String name) {
            return "ThankYou " + name;
        }
    }
    
    @Test
    // ����Ʈ ���� ������ �׽�Ʈ
    public void pointcutAdvisor() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());// Ÿ�� ���� (Ÿ�� ������Ʈ�� �����ϰ� �ִ� �������̽��� ProxyFactoryBean���� ã�ư���)
        
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*"); // �̸� �� ����
        // ��������(����Ʈ�� + �����̽�) �߰�. ����Ʈ�ư� advisor�� ��� �ѹ��� �߰� �׳� ���� �߰��ϴ� ������ �ƴ� ��� ������踦 ������ָ鼭 �߰��ϴ� �����̴�
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice())); 

        Hello proxiedHello = (Hello)pfBean.getObject(); // getObject�� ������ ���Ͻø� ������
        assertEquals(proxiedHello.sayHello("����"), "HELLO ����");
        assertEquals(proxiedHello.sayHi("����"), "HI ����");
        assertNotEquals(proxiedHello.sayThankYou("����"), "THANKYOU ����");
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Ȯ�� ����Ʈ�� �׽�Ʈ
    @Test
    public void classNamePointcutAdvisor() {
        // ����Ʈ �� �غ�
        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
          public ClassFilter getClassFilter() {
              return new ClassFilter() {
                
                @Override
                public boolean matches(Class<?> clazz) {
                    return clazz.getSimpleName().startsWith("HelloT");
                }
            };
          }
        };
        
        classMethodPointcut.setMappedName("sayH*");
        
        checkAdviced(new HelloTarget() , classMethodPointcut, true);
        class HelloWorld extends HelloTarget{};
        checkAdviced(new HelloWorld(), classMethodPointcut, false);
        class HelloWonjun extends HelloTarget{};
        checkAdviced(new HelloWonjun(), classMethodPointcut, false);
    }
    
    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
        Hello proxiedHello = (Hello)pfBean.getObject();
        
        // advice ������ ���� �б�ó��
        if(adviced) {
            assertEquals(proxiedHello.sayHello("����"), "HELLO ����");
            assertEquals(proxiedHello.sayHi("����"), "HI ����");
            assertNotEquals(proxiedHello.sayThankYou("����"), "THANKYOU ����");
        }else {
            assertNotEquals(proxiedHello.sayHello("����"), "HELLO ����");
            assertNotEquals(proxiedHello.sayHi("����"), "HI ����");
            assertNotEquals(proxiedHello.sayThankYou("����"), "THANKYOU ����");
        }
    }
    
    
    
}
