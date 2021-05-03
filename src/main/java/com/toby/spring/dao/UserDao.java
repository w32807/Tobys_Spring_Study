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
    // UserDao가 Application context로 인해, Bean으로 관리된다면...
    // private ConnectionMaker connectionMaker; // 인터페이스
    // ConnectionMaker 대신, DataSource 인터페이스 사용하기
    private DataSource dataSource;
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    // 다음과 같은 인스턴스 변수는, 여러 요청있을 때 동시에 각각의 값을 저장하지 못한다.(멀티스레드 환경에서 문제가 생김)        
    private Connection c;
    private User user;
    
    /*
    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
    */
    public void add(User user) throws ClassNotFoundException, SQLException {
        //Connection c = connectionMaker.makeConnection();
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("insert into users (id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        
        ps.executeUpdate();
        
        ps.close();
        c.close();
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
        Connection c = dataSource.getConnection();
        
        PreparedStatement ps = c.prepareStatement("delete from users");
        ps.executeUpdate();
        ps.close();
        c.close();
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
}
