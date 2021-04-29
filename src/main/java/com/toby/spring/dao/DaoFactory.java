package com.toby.spring.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

//@Configuration // ���ø����̼� ���ؽ�Ʈ �Ǵ� �� ���丮�� ����� ����������� ǥ��
public class DaoFactory {
    // ���丮��, ��ü�� ���� ����� �����ϰ� �׷��� ������� ������Ʈ�� �����ִ� Ŭ������ �ǹ�
    
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("");
        dataSource.setUsername("");
        dataSource.setPassword("");
        
        return dataSource;
    }
    
    // DConnection�� ���� ��ȯ
    @Bean // ������Ʈ ������ ����Ѵ� Ioc�� �޼ҵ��� ǥ��
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }
    
    @Bean
    public UserDao adminDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }
    
    @Bean
    public UserDao messageDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }
            
    // Dao�� ���� �� �����ص�, connectionMaker()�� �����ϸ� �ǹǷ� ���ѵ��� ��
    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
