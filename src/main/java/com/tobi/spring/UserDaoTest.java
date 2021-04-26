package com.tobi.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tobi.spring.dao.DaoFactory;
import com.tobi.spring.dao.UserDao;

public class UserDaoTest {

    public static void main(String[] args) {
        // 스프링 Legacy 프로젝트가 아닐 때 ApplicationContext 사용하기
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        // 등록된 Bean의 이름, 클래스 를 매개변수로 Bean을 얻어온다.
        UserDao dao = context.getBean("userDao", UserDao.class);
        /*
        ConnectionMaker connectionMaker = new DConnectionMaker();
        
        // connection을 만드는 역할을, 클라이언트가 아닌 팩토리로 넘겨버림
        UserDao dao = new DaoFactory().userDao();
        System.out.println(dao);
        */
    }
}
