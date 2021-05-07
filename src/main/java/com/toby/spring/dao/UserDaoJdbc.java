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
    // UserDao가 Application context로 인해, Bean으로 관리된다면...
    // private ConnectionMaker connectionMaker; // 인터페이스
    // ConnectionMaker 대신, DataSource 인터페이스 사용하기
    private DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate; // JDBC에서 제공하는 템플릿 이용
    
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
    public void add(User user){
        /*
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
        */
        //JdbcTemplate 적용
        // insert 시 중복 key 오류는 특정한 예외로 처리되도록 수정
        /*
        try {
            // JDBC SQLException 발생할 수 있는 코드...
            class AddStatement implements StatementStrategy{
                @Override
                public PreparedStatement makePreparedStatement(Connection c) throws SQLException , SQLIntegrityConstraintViolationException{ // SQL의 중복키 오류 예외
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
        // JdbcTemplate 적용 2가지 방법
        return this.jdbcTemplate.queryForObject(" select * from users where id = ? ", new Object[] {id}, this.userMapper);
    }
    
    public void deleteAll(){
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
        //this.jdbcContext.excuteSql("delete from users");
        
        //JdbcTemplate 적용 2가지 방법
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
        //JdbcTemplate 적용 2가지 방법
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
    
    @Override
    public void update(User user) {
        this.jdbcTemplate.update("update users set name = ?, password = ?, Level = ?, Login = ?, Recommand = ? where id = ? "
                ,user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommand(), user.getId());
    }
}
