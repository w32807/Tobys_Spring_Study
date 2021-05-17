package com.toby.spring.proxy;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

// Ŭ���� ���� ����� ���� NameMatchMethodPointcut�� ��ӹ޾� Ŭ���� ���� ����� ������ 
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
            // getSimpleName�� ��Ű�� ��ΰ� ���Ե��� ���� ���� Ŭ������ ������ �� ���
            return PatternMatchUtils.simpleMatch(mappedName, clazz.getSimpleName());
        }
    }
}
