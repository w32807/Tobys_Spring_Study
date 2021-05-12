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
        // 혹시 모르니, 운영 DB가 아닌 테스트 DB로 명시적인 연결을 해준다.
        // 근데 이 방법을 쓰려면, 꼭 dao는 datasource를 생성자로써 주입 받아야 하나?
        // 그러나 이 방법보다, test-applicationContext.xml 파일을 만드는 것이 더 깔끔.
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
