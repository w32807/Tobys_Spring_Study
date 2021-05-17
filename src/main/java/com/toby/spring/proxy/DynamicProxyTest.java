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
    // Dynamic proxy 생성 (직접 생성)
    public void simpleProxy() {
        Hello proxiedHello = (Hello)Proxy.newProxyInstance(
                                getClass().getClassLoader(), 
                                new Class[] {Hello.class}
                                , new UppercaseHandler(new HelloTarget()));
    }
    
    @Test
    // Dynamic proxy 생성 (FactoryBean 사용)
    // advice는 타깃에 얽매이지 않고 순수한 부가기능으로써 작동한다.
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());// 타겟 설정 (타깃 오브젝트가 구현하고 있는 인터페이스를 ProxyFactoryBean에서 찾아간다)
        pfBean.addAdvice(new UppercaseAdvice());// 부가기능을 담은 어드바이스 추가 (여러 개도 가능)

        Hello proxiedHello = (Hello)pfBean.getObject(); // getObject로 생성된 프록시를 가져옴
        assertEquals(proxiedHello.sayHello("원준"), "HELLO 원준");
        assertEquals(proxiedHello.sayHi("원준"), "HI 원준");
        assertEquals(proxiedHello.sayThankYou("원준"), "THANKYOU 원준");
    }
    
    static class UppercaseAdvice implements MethodInterceptor{
        public Object invoke(MethodInvocation invocation) throws Throwable{
            String ret = (String)invocation.proceed(); // 리플렉션의 Method와 달리 메소드 실행 시 타깃 오브젝트를 전달할 필요가 없다
                                                       // MethodInvocation는 메소드 정보와 함께 타깃 오브젝트를 알고 있기 때문이다.
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
    // 포인트 컷을 적용한 테스트
    public void pointcutAdvisor() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());// 타겟 설정 (타깃 오브젝트가 구현하고 있는 인터페이스를 ProxyFactoryBean에서 찾아간다)
        
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*"); // 이름 비교 설정
        // 어드바이저(포인트컷 + 어드바이스) 추가. 포인트컷과 advisor를 묶어서 한번에 추가 그냥 같이 추가하는 개념이 아닌 묶어서 연결관계를 만들어주면서 추가하는 개념이다
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice())); 

        Hello proxiedHello = (Hello)pfBean.getObject(); // getObject로 생성된 프록시를 가져옴
        assertEquals(proxiedHello.sayHello("원준"), "HELLO 원준");
        assertEquals(proxiedHello.sayHi("원준"), "HI 원준");
        assertNotEquals(proxiedHello.sayThankYou("원준"), "THANKYOU 원준");
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 확장 포인트컷 테스트
    @Test
    public void classNamePointcutAdvisor() {
        // 포인트 컷 준비
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
        
        // advice 적용대상에 따른 분기처리
        if(adviced) {
            assertEquals(proxiedHello.sayHello("원준"), "HELLO 원준");
            assertEquals(proxiedHello.sayHi("원준"), "HI 원준");
            assertNotEquals(proxiedHello.sayThankYou("원준"), "THANKYOU 원준");
        }else {
            assertNotEquals(proxiedHello.sayHello("원준"), "HELLO 원준");
            assertNotEquals(proxiedHello.sayHi("원준"), "HI 원준");
            assertNotEquals(proxiedHello.sayThankYou("원준"), "THANKYOU 원준");
        }
    }
    
    
    
}
