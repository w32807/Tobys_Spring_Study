package com.toby.spring.proxy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProxyTests {
    
    @Test
    public void simpleTest() {
        // ���Ͻø� ���� Hello�� ������ ���� �ƴ�, ���� �����Ͽ���.
        Hello hello = new HelloTarget(); // Ÿ���� �׻� �������̽��� ���� �����ϵ��� ����
        assertEquals(hello.sayHello("����"), "Hello ����");
        assertEquals(hello.sayHi("����"), "Hi ����");
        assertEquals(hello.sayThankYou("����"), "ThankYou ����");
    }
    
    @Test
    public void createHelloProxyTest() {
        // ���Ͻ� : HelloUpperCase, Ÿ�� : HelloTarget
        Hello proxieHello = new HelloUpperCase(new HelloTarget());
        assertEquals(proxieHello.sayHello("����"), "HELLO ����");
        assertEquals(proxieHello.sayHi("����"), "HI ����");
        assertEquals(proxieHello.sayThankYou("����"), "THANK +YOU ����");
    }
}
