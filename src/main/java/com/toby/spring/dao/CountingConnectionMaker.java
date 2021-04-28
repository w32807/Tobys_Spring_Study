package com.toby.spring.dao;

import java.sql.Connection;
import java.sql.SQLException;

// DB ����Ƚ�� Counting Ŭ����
public class CountingConnectionMaker implements ConnectionMaker{
    int counter = 0;
    private ConnectionMaker realConnectionMaker;
    
    public CountingConnectionMaker(ConnectionMaker reConnectionMaker) {
        this.realConnectionMaker = reConnectionMaker;
    }
    
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // counter�� ������Ű��, �����ڿ��� ���� connectionMaker��ü�� �״�� �����ش�.
        counter++;
        return realConnectionMaker.makeConnection();
    }

    public int getCounter() {
        return this.counter;
    }
    
}
