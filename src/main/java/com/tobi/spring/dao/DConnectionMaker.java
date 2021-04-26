package com.tobi.spring.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker{

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        // D사의 독자적 Connection 메소드..
        return null;
    }
}
