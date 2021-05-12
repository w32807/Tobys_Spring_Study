package com.toby.spring.dao;

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

import com.toby.spring.domain.Level;
import com.toby.spring.domain.User;
import com.toby.spring.service.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@DirtiesContext // 원래 설정한 스프링의 설정이 아닌, 테스트 코드에서 컨텍스트 설정이 바뀔 것을 알려주는 어노테이션
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
        
        user1 = new User("id1","name1","ps1", Level.BASIC, 1,0, "email1");
        user2 = new User("id2","name2","ps2", Level.BASIC, 1,0, "email2");
        user3 = new User("id3","name3","ps3", Level.BASIC, 1,0, "email3");
    }
    @Test
    public void insertTest() throws Exception{
        dao.deleteAll();
        
        dao.add(user1);
        User user2 = dao.get(user1.getId());
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
    
    @Test
    public void addAndGet() {
        dao.deleteAll();
        dao.add(user1);
        dao.add(user2);
        User userget1 = dao.get(user1.getId());
        chkSameUser(userget1, user1);
        User userget2 = dao.get(user2.getId());
        chkSameUser(userget2, user2);
    }
    
    @Test
    public void update() {
        dao.deleteAll();
        
        dao.add(user1);
        dao.add(user2);
        
        user1.setName("장원준");
        user1.setPassword("10121");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommand(999);
        dao.update(user1);
        
        User user1update = dao.get(user1.getId());
        chkSameUser(user1update, user1);
        User user2same = dao.get(user2.getId());
        chkSameUser(user2, user2same);
        
    }
    
    private void chkSameUser(User user1, User user2) {
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getName(), user2.getName());
        assertEquals(user1.getPassword(), user2.getPassword());
        assertEquals(user1.getLevel(), user2.getLevel());
        assertEquals(user1.getLogin(), user2.getLogin());
        assertEquals(user1.getRecommand(), user2.getRecommand());
    }
}
