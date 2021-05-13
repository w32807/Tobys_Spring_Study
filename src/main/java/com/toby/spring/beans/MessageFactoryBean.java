package com.toby.spring.beans;

import org.springframework.beans.factory.FactoryBean;

public class MessageFactoryBean implements FactoryBean<Message>{
    String text;
    
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Message getObject() throws Exception {
        return Message.newMessage(text);
    }
    
    @Override
    public Class<? extends Message> getObjectType() {
        return Message.class;
    }
    
    @Override
    public boolean isSingleton() { //getObject() 를 통해 매번 생성하므로, false 값을 리턴
        return false;
    }
    
}
