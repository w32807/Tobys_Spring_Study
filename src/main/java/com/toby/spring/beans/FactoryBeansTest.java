package com.toby.spring.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class FactoryBeansTest {
    @Autowired
    ApplicationContext context;
    
    @Test
    public void getMessageFromFactoryBean() {
        Object message = context.getBean("message");
        System.out.println(message.getClass());
        assertEquals(message.getClass(), Message.class);// 타입확인
        assertEquals(((Message)message).getText(), "Factory Bean");// 기능확인
    }
}
