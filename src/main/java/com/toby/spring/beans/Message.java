package com.toby.spring.beans;

// 팩토리 빈 테스트
public class Message {
    String text;
    
    private Message(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    // 생성자가 private이므로 생성자 대신 사용할 수 있는 static 팩토리 메소드를 제공
    public static Message newMessage(String text) {
        return new Message(text);
    }
}
