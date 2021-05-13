package com.toby.spring.proxy;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Test;

public class ReflectionTest {
    
    @Test
    public void invokeMethod() throws Exception{
        String name = "Spring";
        assertEquals(name.length(), 6);
        assertEquals(name.length(), 6);
        
        Method lengthMethod = String.class.getMethod("length");
        assertEquals(lengthMethod.invoke(name), 6);
        
        assertEquals(name.charAt(0), 'S');
        
        Method charAtMethod = String.class.getMethod("charAt", int.class);
        assertEquals(charAtMethod.invoke(name, 0), 'S');
        
        
        
        
        
    }
}
