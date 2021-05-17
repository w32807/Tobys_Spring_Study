package com.toby.spring.proxy;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

// 클래스 필터 기능이 없는 NameMatchMethodPointcut를 상속받아 클래스 필터 기능을 더하자 
public class NameMatchClassMethodPointcut extends NameMatchMethodPointcut{
    
    public void setMappedClassName(String mappedClassName) {
        this.setClassFilter(new SimpleClassFilter(mappedClassName));
    }
    
    static class SimpleClassFilter implements ClassFilter{
        String mappedName;
        
        
        public SimpleClassFilter(String mappedName) {
            this.mappedName = mappedName;
        }

        @Override
        public boolean matches(Class<?> clazz) {
            // getSimpleName는 패키지 경로가 포함되지 않은 순수 클래스명만 가져올 때 사용
            return PatternMatchUtils.simpleMatch(mappedName, clazz.getSimpleName());
        }
    }
}
