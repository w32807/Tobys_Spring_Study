package com.toby.spring.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker{

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // N���� ������ Connection �޼ҵ�..
        return null;
    }
}
