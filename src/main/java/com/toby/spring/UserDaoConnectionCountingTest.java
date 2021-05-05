package com.toby.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.toby.spring.dao.CountingConnectionMaker;
import com.toby.spring.dao.CountingDaoFactory;
import com.toby.spring.dao.DaoFactory;
import com.toby.spring.dao.UserDaoJdbc;

public class UserDaoConnectionCountingTest {

    public static void main(String[] args) {
        // ������ Legacy ������Ʈ�� �ƴ� �� ApplicationContext ����ϱ�
        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        // ��ϵ� Bean�� �̸�, Ŭ���� �� �Ű������� Bean�� ���´�.
        UserDaoJdbc dao = context.getBean("userDao", UserDaoJdbc.class);
        
        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter >> " + ccm.getCounter());
    }
}
