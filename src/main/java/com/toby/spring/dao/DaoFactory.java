package com.toby.spring.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

//@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
    // 팩토리란, 객체의 생성 방법을 결정하고 그렇게 만들어진 오브젝트를 돌려주는 클래스를 의미
    
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("");
        dataSource.setUsername("");
        dataSource.setPassword("");
        
        return dataSource;
    }
    
    // DConnection을 만들어서 반환
    @Bean // 오브젝트 생성을 담당한느 Ioc용 메소드라는 표시
    public UserDaoJdbc userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        return userDao;
    }
    
    @Bean
    public UserDaoJdbc adminDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        return userDao;
    }
    
    @Bean
    public UserDaoJdbc messageDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        return userDao;
    }
            
    // Dao를 여러 개 선언해도, connectionMaker()만 수정하면 되므로 리팩도링 됨
    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
