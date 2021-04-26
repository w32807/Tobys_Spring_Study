package com.tobi.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tobi.spring.dao.DaoFactory;
import com.tobi.spring.dao.UserDao;

public class UserDaoTest {

    public static void main(String[] args) {
        // ������ Legacy ������Ʈ�� �ƴ� �� ApplicationContext ����ϱ�
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        // ��ϵ� Bean�� �̸�, Ŭ���� �� �Ű������� Bean�� ���´�.
        UserDao dao = context.getBean("userDao", UserDao.class);
        /*
        ConnectionMaker connectionMaker = new DConnectionMaker();
        
        // connection�� ����� ������, Ŭ���̾�Ʈ�� �ƴ� ���丮�� �Ѱܹ���
        UserDao dao = new DaoFactory().userDao();
        System.out.println(dao);
        */
    }
}
