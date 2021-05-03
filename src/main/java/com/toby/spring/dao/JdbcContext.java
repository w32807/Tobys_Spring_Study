package com.toby.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {
    private DataSource dataSource;
    
    public void setDatasource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    // workWithStatementStrategy 메소드가 템플릿 역할
    // makePreparedStatement 메소드가 콜백 오브젝트 역할
    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;
        
        try {
            c = dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if(c != null) {
                try {
                    c.close();
                } catch (SQLException e ) {
                }
            }
        }
    }
    
    // 쿼리 전달하는 메소드를 따로 작성
    public void excuteSql(final String query) throws SQLException{
        workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                return c.prepareStatement(query);
            }
        });
    }

}
