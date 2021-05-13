package com.toby.spring.proxy;

public class HelloUpperCase implements Hello{
    Hello hello; // ���Ͻÿ� Ÿ���� ������ Hello �������̽�
    
    public HelloUpperCase(Hello hello) {
        // ���Ͻ� Ŭ������ HelloUpperCase�� Ÿ�� (�ٸ� ���ڷ��̼� ���Ͻð� �� ����) �� �����Ѵ�. 
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
