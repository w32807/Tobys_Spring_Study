package com.tobi.spring.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
    // 팩토리란, 객체의 생성 방법을 결정하고 그렇게 만들어진 오브젝트를 돌려주는 클래스를 의미
    
    // DConnection을 만들어서 반환
    @Bean // 오브젝트 생성을 담당한느 Ioc용 메소드라는 표시
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
    
    // Dao를 여러 개 선언해도, connectionMaker()만 수정하면 되므로 리팩도링 됨
    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
