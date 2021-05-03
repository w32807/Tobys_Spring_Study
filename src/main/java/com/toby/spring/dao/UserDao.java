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
    
    private JdbcContext jdbcContext;
    
    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    /*
    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
    */
    // 로컬 클래스에 변수를 전달하기 위해서 final로 선언해야 한다.
    public void add(final User user) throws ClassNotFoundException, SQLException {
        // add 메소드에서만 사용하는 class 이므로 로컬 클래스로 선언!
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

        PreparedStatement ps = c.prepareStatement("delete from users"); // 만약 여기서 오류가 나면, Connection은 닫히지 않은 채 유지된다.
        ps.executeUpdate();
        ps.close();
        c.close();
        */
        // 익명클래스로 전달
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
    
    // DB 연결과, 자원 해제까지 공통으로 사용되는 메소드 -> JdbcContext 클래스로 이동
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
