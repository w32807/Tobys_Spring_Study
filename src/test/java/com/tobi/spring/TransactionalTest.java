package com.tobi.spring;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.toby.spring.dao.UserDaoJdbc;
import com.toby.spring.domain.Level;
import com.toby.spring.domain.User;
import com.toby.spring.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TransactionalTest {

    @Autowired
    private UserDaoJdbc dao;
    
    @Autowired
    UserService userService;
    
    private User user1;
    private User user2;
    private User user3;
    @Before
    public void setUp() {
        // Ȥ�� �𸣴�, � DB�� �ƴ� �׽�Ʈ DB�� ������� ������ ���ش�.
        // �ٵ� �� ����� ������, �� dao�� datasource�� �����ڷν� ���� �޾ƾ� �ϳ�?
        // �׷��� �� �������, test-applicationContext.xml ������ ����� ���� �� ���.
        DataSource dataSource = new SingleConnectionDataSource(
            "jdbc:mysql://localhost/tobySpring?serverTimezone=Asia/Seoul"
                ,"root", "1234", true);
        dao.setDataSource(dataSource);
        
        user1 = new User("id1","name1","ps1", Level.BASIC, 1,0 , "email");
        user2 = new User("id2","name2","ps2", Level.BASIC, 1,0 , "email");
        user3 = new User("id3","name3","ps3", Level.BASIC, 1,0 , "email");
    }
    
    @Test
    public void addAndUpgradeTest() {
        dao.deleteAll();
        userService.addAndUpdate(user1);
    }
}
