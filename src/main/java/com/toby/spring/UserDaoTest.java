 package com.toby.spring;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.toby.spring.dao.UserDao;
import com.toby.spring.domain.User;

public class UserDaoTest {
    // JUnit�� Test �ڵ�� ��ȯ
    /*
    public static void main(String[] args) {
        // ������ Legacy ������Ʈ�� �ƴ� �� ApplicationContext ����ϱ�
        // ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        // ��ϵ� Bean�� �̸�, Ŭ���� �� �Ű������� Bean�� ���´�.
        
        // XML ���������� ���
        // ������ ���� �𸣰�����, UserDao�� ������ �����ڰ� �ƴ� setter ������� ���� �ؾ� ������ �ȳ�.
        ApplicationContext context = new GenericXmlApplicationContext("/applicationContext.xml");
        
        UserDao dao1 = context.getBean("userDao", UserDao.class);
        UserDao dao2 = context.getBean("userDao", UserDao.class);
        
        System.out.println(dao1 == dao2);
        System.out.println(dao1.equals(dao2));
        ConnectionMaker connectionMaker = new DConnectionMaker();
        
        // connection�� ����� ������, Ŭ���̾�Ʈ�� �ƴ� ���丮�� �Ѱܹ���
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
        user.setName("����");
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
