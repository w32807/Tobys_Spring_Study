 package com.toby.spring;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.toby.spring.dao.UserDaoJdbc;
import com.toby.spring.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@DirtiesContext // 원래 설정한 스프링의 설정이 아닌, 테스트 코드에서 컨텍스트 설정이 바뀔 것을 알려주는 어노테이
public class UserDaoTest {
    private User user1;
    private User user2;
    private User user3;
    
    @Autowired
    private UserDaoJdbc dao;
    
    @Before
    public void setUp() {
        // 혹시 모르니, 운영 DB가 아닌 테스트 DB로 명시적인 연결을 해준다.
        // 근데 이 방법을 쓰려면, 꼭 dao는 datasource를 생성자로써 주입 받아야 하나?
        // 그러나 이 방법보다, test-applicationContext.xml 파일을 만드는 것이 더 깔끔.
        DataSource dataSource = new SingleConnectionDataSource(
            "jdbc:mysql://localhost/tobySpring?serverTimezone=Asia/Seoul"
                ,"root", "1234", true);
        dao.setDataSource(dataSource);
        
        user1 = new User();
        user2 = new User();
        user3 = new User();
        
        user1.setId("id1");
        user1.setName("name1");
        user1.setPassword("ps1");
        user2.setId("id2");
        user2.setName("name2");
        user2.setPassword("ps2");
        user3.setId("id3");
        user3.setPassword("ps3");
        user3.setName("name3");
    }
    @Test
    public void insertTest() throws Exception{
        dao.deleteAll();
        
        User user = new User();
        user.setId("user");
        user.setName("원준");
        user.setPassword("1234");
        dao.add(user);
        User user2 = dao.get(user.getId());
        System.out.println(user2.getId());
        
    }
    
    // 테스트 도중, 발생할 것으로 기대하는 예외클래스를 지정함
    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws Exception{
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);
        dao.get("unknown_id");
    }
    
    // 모든 User를 가져오는 메소드
    @Test
    public void getAll() throws Exception{
        dao.deleteAll();
        dao.add(user1);
        dao.add(user2);
        dao.add(user3);
        
        dao.getAll();
    }
    
    @Test(expected = DataAccessException.class)
    public void duplicateKey() {
        dao.deleteAll();
        dao.add(user1);
        dao.add(user1);
    }
}
