package com.toby.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://mydbinstance.cnteb2qmglp1.ap-northeast-2.rds.amazonaws.com:3306/atelier"
                , "admin" , "dnjswns123");
    }

}
