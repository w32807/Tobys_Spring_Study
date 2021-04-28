package com.toby.spring.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration // ���ø����̼� ���ؽ�Ʈ �Ǵ� �� ���丮�� ����� ����������� ǥ��
public class DaoFactory {
    // ���丮��, ��ü�� ���� ����� �����ϰ� �׷��� ������� ������Ʈ�� �����ִ� Ŭ������ �ǹ�
    
    // DConnection�� ���� ��ȯ
    @Bean // ������Ʈ ������ ����Ѵ� Ioc�� �޼ҵ��� ǥ��
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(connectionMaker());
        return userDao;
    }
    
    @Bean
    public UserDao adminDao() {
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(connectionMaker());
        return userDao;
    }
    
    @Bean
    public UserDao messageDao() {
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(connectionMaker());
        return userDao;
    }
    
    // Dao�� ���� �� �����ص�, connectionMaker()�� �����ϸ� �ǹǷ� ���ѵ��� ��
    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
