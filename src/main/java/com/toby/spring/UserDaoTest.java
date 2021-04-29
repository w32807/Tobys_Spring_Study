 package com.toby.spring;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.toby.spring.dao.UserDao;
import com.toby.spring.domain.User;

public class UserDaoTest {
    // JUnit의 Test 코드로 전환
    /*
    public static void main(String[] args) {
        // 스프링 Legacy 프로젝트가 아닐 때 ApplicationContext 사용하기
        // ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        // 등록된 Bean의 이름, 클래스 를 매개변수로 Bean을 얻어온다.
        
        // XML 설정파일일 경우
        // 왜인지 아직 모르겠지만, UserDao의 주입을 생성자가 아닌 setter 방식으로 변경 해야 오류가 안남.
        ApplicationContext context = new GenericXmlApplicationContext("/applicationContext.xml");
        
        UserDao dao1 = context.getBean("userDao", UserDao.class);
        UserDao dao2 = context.getBean("userDao", UserDao.class);
        
        System.out.println(dao1 == dao2);
        System.out.println(dao1.equals(dao2));
        ConnectionMaker connectionMaker = new DConnectionMaker();
        
        // connection을 만드는 역할을, 클라이언트가 아닌 팩토리로 넘겨버림
        UserDao dao = new DaoFactory().userDao();
        System.out.println(dao);
    }
    */
    
    @Test
    public void insertTest() {
        ApplicationContext context = new GenericXmlApplicationContext("/applicationContext.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);
        
        User user = new User();
        
        user.setId("user");
        user.setName("원준");
        user.setPassword("1234");
        try {
            dao.add(user);
            User user2 = dao.get(user.getId());
            System.out.println(user2.getId());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
    }
}
