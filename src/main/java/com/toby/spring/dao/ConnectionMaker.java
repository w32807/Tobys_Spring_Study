package com.toby.spring.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.management.MXBean;

public interface ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException; 
}
