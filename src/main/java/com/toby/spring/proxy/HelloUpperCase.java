package com.toby.spring.proxy;

public class HelloUpperCase implements Hello{
    Hello hello; // 프록시와 타깃의 상위인 Hello 인터페이스
    
    public HelloUpperCase(Hello hello) {
        // 프록시 클래스인 HelloUpperCase에 타깃 (다른 데코레이션 프록시가 될 수도) 을 주입한다. 
        this.hello = hello;
    }
    
    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase();
    }
    
    @Override
    public String sayHi(String name) {
        return hello.sayHi(name).toUpperCase();
    }
    
    @Override
    public String sayThankYou(String name) {
        return hello.sayThankYou(name).toUpperCase();
    }
}
