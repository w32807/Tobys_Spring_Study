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
@DirtiesContext // ���� ������ �������� ������ �ƴ�, �׽�Ʈ �ڵ忡�� ���ؽ�Ʈ ������ �ٲ� ���� �˷��ִ� ������̼�
public class UserDaoTest {
    private User user1;
    private User user2;
    private User user3;
    
    @Autowired
    private UserDaoJdbc dao;
    
    @Before
    public void setUp() {
        // Ȥ�� �𸣴�, � DB�� �ƴ� �׽�Ʈ DB�� ������� ������ ���ش�.
        // �ٵ� �� ����� ������, �� dao�� datasource�� �����ڷν� ���� �޾ƾ� �ϳ�?
        // �׷��� �� �������, test-applicationContext.xml ������ ����� ���� �� ���.
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
    
    // �׽�Ʈ ����, �߻��� ������ ����ϴ� ����Ŭ������ ������
    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws Exception{
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);
        dao.get("unknown_id");
    }
    
    // ��� User�� �������� �޼ҵ�
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
        
        user1.setName("�����");
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
