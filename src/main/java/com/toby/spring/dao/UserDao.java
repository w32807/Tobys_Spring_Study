package com.toby.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import com.toby.spring.domain.User;

public class UserDao {
    // UserDao�� Application context�� ����, Bean���� �����ȴٸ�...
    // private ConnectionMaker connectionMaker; // �������̽�
    // ConnectionMaker ���, DataSource �������̽� ����ϱ�
    private DataSource dataSource;
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    // ������ ���� �ν��Ͻ� ������, ���� ��û���� �� ���ÿ� ������ ���� �������� ���Ѵ�.(��Ƽ������ ȯ�濡�� ������ ����)        
    private Connection c;
    private User user;
    
    private JdbcContext jdbcContext;
    
    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    /*
    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
    */
    // ���� Ŭ������ ������ �����ϱ� ���ؼ� final�� �����ؾ� �Ѵ�.
    public void add(final User user) throws ClassNotFoundException, SQLException {
        // add �޼ҵ忡���� ����ϴ� class �̹Ƿ� ���� Ŭ������ ����!
        class AddStatement implements StatementStrategy{
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("insert into users (id, name, password) values(?,?,?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                
                return ps;
            }
        }
        StatementStrategy strategy = new AddStatement();
        //jdbcContextWirhStatementStrategy(strategy);
        this.jdbcContext.workWithStatementStrategy(strategy);
    }
    
    public User get(String id) throws ClassNotFoundException, SQLException {
        //Connection c = connectionMaker.makeConnection();
        Connection c = dataSource.getConnection();
        
        PreparedStatement ps = c.prepareStatement(" select * from users where id = ? ");
        ps.setString(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        User user = null;
        if(rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }
        
        rs.close();
        ps.close();
        c.close();
        if(user == null) throw new EmptyResultDataAccessException(1); 
        return user;
    }
    
    public void deleteAll() throws SQLException{
        /*Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("delete from users"); // ���� ���⼭ ������ ����, Connection�� ������ ���� ä �����ȴ�.
        ps.executeUpdate();
        ps.close();
        c.close();
        */
        // �͸�Ŭ������ ����
        /*
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                return c.prepareStatement("delete from users");
            }
        });
        */
        this.jdbcContext.excuteSql("delete from users");
    }
    
    public int getCount() throws SQLException{
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("select Count(*) from users");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        ps.close();
        c.close();
        
        return count;
    }
    
    // DB �����, �ڿ� �������� �������� ���Ǵ� �޼ҵ� -> JdbcContext Ŭ������ �̵�
    /*
    public void jdbcContextWirhStatementStrategy(StatementStrategy stmt) throws SQLException {
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
    */
}
