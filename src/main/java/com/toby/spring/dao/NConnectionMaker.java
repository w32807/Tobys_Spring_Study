package com.toby.spring.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker{

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // N사의 독자적 Connection 메소드..
        return null;
    }
}
