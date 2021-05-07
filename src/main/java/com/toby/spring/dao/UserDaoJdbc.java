package com.toby.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import com.toby.spring.domain.Level;
import com.toby.spring.domain.User;

public class UserDaoJdbc implements UserDao{
    // UserDao�� Application context�� ����, Bean���� �����ȴٸ�...
    // private ConnectionMaker connectionMaker; // �������̽�
    // ConnectionMaker ���, DataSource �������̽� ����ϱ�
    private DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate; // JDBC���� �����ϴ� ���ø� �̿�
    
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
    public void add(User user){
        /*
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
        */
        //JdbcTemplate ����
        // insert �� �ߺ� key ������ Ư���� ���ܷ� ó���ǵ��� ����
        /*
        try {
            // JDBC SQLException �߻��� �� �ִ� �ڵ�...
            class AddStatement implements StatementStrategy{
                @Override
                public PreparedStatement makePreparedStatement(Connection c) throws SQLException , SQLIntegrityConstraintViolationException{ // SQL�� �ߺ�Ű ���� ����
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
        } catch (SQLException e) {
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
                //throw DuplicateUserIdException();
            }else {
                throw e;
            }
        }
        */
        this.jdbcTemplate.update("insert into users (id, name, password, Level, Login, Recommand) values(?,?,?,?,?,?)"
                , user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommand());
    }
    
    public User get(String id){
        /*
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
        */
        // JdbcTemplate ���� 2���� ���
        return this.jdbcTemplate.queryForObject(" select * from users where id = ? ", new Object[] {id}, this.userMapper);
    }
    
    public void deleteAll(){
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
        //this.jdbcContext.excuteSql("delete from users");
        
        //JdbcTemplate ���� 2���� ���
        /*
        this.jdbcTemplate.update(new PreparedStatementCreator() {
            
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement("delete from users");
            }
        });
        */
        this.jdbcTemplate.update("delete from users");
    }
    
    public int getCount(){
        /*
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("select Count(*) from users");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        ps.close();
        c.close();
        
        return count;
        */
        //JdbcTemplate ���� 2���� ���
        /*
        return this.jdbcTemplate.query(new PreparedStatementCreator() {
            
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement("select Count(*) from users");
            }
        }, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                rs.next();
                return rs.getInt(1);
            }
        });
        */
        return this.jdbcTemplate.queryForInt("select Count(*) from users");
    }
    
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
    }
    
    private RowMapper<User> userMapper = new RowMapper<User>() {
        
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("Level")));
            user.setLogin(rs.getInt("Login"));
            user.setRecommand(rs.getInt("Recommand"));
            return user;
        }
    };
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
    
    @Override
    public void update(User user) {
        this.jdbcTemplate.update("update users set name = ?, password = ?, Level = ?, Login = ?, Recommand = ? where id = ? "
                ,user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommand(), user.getId());
    }
}
