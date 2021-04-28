package com.toby.spring.dao;

import java.sql.Connection;
import java.sql.SQLException;

// DB 연결횟수 Counting 클래스
public class CountingConnectionMaker implements ConnectionMaker{
    int counter = 0;
    private ConnectionMaker realConnectionMaker;
    
    public CountingConnectionMaker(ConnectionMaker reConnectionMaker) {
        this.realConnectionMaker = reConnectionMaker;
    }
    
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // counter만 증가시키고, 생성자에서 받은 connectionMaker객체를 그대로 돌려준다.
        counter++;
        return realConnectionMaker.makeConnection();
    }

    public int getCounter() {
        return this.counter;
    }
    
}
