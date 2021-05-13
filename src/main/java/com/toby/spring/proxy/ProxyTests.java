package com.toby.spring.proxy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProxyTests {
    
    @Test
    public void simpleTest() {
        // 프록시를 통해 Hello에 접근한 것이 아닌, 직접 접근하였다.
        Hello hello = new HelloTarget(); // 타깃은 항상 인터페이스를 통해 접근하도록 하자
        assertEquals(hello.sayHello("원준"), "Hello 원준");
        assertEquals(hello.sayHi("원준"), "Hi 원준");
        assertEquals(hello.sayThankYou("원준"), "ThankYou 원준");
    }
    
    @Test
    public void createHelloProxyTest() {
        // 프록시 : HelloUpperCase, 타깃 : HelloTarget
        Hello proxieHello = new HelloUpperCase(new HelloTarget());
        assertEquals(proxieHello.sayHello("원준"), "HELLO 원준");
        assertEquals(proxieHello.sayHi("원준"), "HI 원준");
        assertEquals(proxieHello.sayThankYou("원준"), "THANK +YOU 원준");
    }
}
