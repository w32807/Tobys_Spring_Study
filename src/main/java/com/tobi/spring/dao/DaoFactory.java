package com.tobi.spring.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // ���ø����̼� ���ؽ�Ʈ �Ǵ� �� ���丮�� ����� ����������� ǥ��
public class DaoFactory {
    // ���丮��, ��ü�� ���� ����� �����ϰ� �׷��� ������� ������Ʈ�� �����ִ� Ŭ������ �ǹ�
    
    // DConnection�� ���� ��ȯ
    @Bean // ������Ʈ ������ ����Ѵ� Ioc�� �޼ҵ��� ǥ��
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }
    
    @Bean
    public UserDao adminDao() {
        return new UserDao(connectionMaker());
    }
    
    @Bean
    public UserDao messageDao() {
        return new UserDao(connectionMaker());
    }
    
    // Dao�� ���� �� �����ص�, connectionMaker()�� �����ϸ� �ǹǷ� ���ѵ��� ��
    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
