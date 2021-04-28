package com.toby.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.toby.spring.dao.UserDao;

public class UserDaoTest {

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
        /*
        ConnectionMaker connectionMaker = new DConnectionMaker();
        
        // connection�� ����� ������, Ŭ���̾�Ʈ�� �ƴ� ���丮�� �Ѱܹ���
        UserDao dao = new DaoFactory().userDao();
        System.out.println(dao);
        */
    }
}
