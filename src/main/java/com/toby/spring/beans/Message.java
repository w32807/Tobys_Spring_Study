package com.toby.spring.beans;

// ���丮 �� �׽�Ʈ
public class Message {
    String text;
    
    private Message(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    // �����ڰ� private�̹Ƿ� ������ ��� ����� �� �ִ� static ���丮 �޼ҵ带 ����
    public static Message newMessage(String text) {
        return new Message(text);
    }
}
