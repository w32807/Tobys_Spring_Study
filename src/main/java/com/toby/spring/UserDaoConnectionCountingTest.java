package com.toby.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.toby.spring.dao.CountingConnectionMaker;
import com.toby.spring.dao.CountingDaoFactory;
import com.toby.spring.dao.DaoFactory;
import com.toby.spring.dao.UserDaoJdbc;

public class UserDaoConnectionCountingTest {

    public static void main(String[] args) {
        // 스프링 Legacy 프로젝트가 아닐 때 ApplicationContext 사용하기
        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        // 등록된 Bean의 이름, 클래스 를 매개변수로 Bean을 얻어온다.
        UserDaoJdbc dao = context.getBean("userDao", UserDaoJdbc.class);
        
        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter >> " + ccm.getCounter());
    }
}
