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
@DirtiesContext // ���� ������ �������� ������ �ƴ�, �׽�Ʈ �ڵ忡�� ���ؽ�Ʈ ������ �ٲ� ���� �˷��ִ� �������
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
        user.setName("����");
        user.setPassword("1234");
        dao.add(user);
        User user2 = dao.get(user.getId());
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
}
