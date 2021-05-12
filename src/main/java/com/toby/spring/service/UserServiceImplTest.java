package com.toby.spring.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.toby.spring.dao.UserDao;
import com.toby.spring.domain.Level;
import com.toby.spring.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserServiceImplTest {

    @Autowired
    UserServiceImpl userServiceImpl;
    
    @Autowired
    PlatformTransactionManager transactionManager;
    
    @Autowired
    UserDao userDao;
    
    List<User> users;
    
    @Before
    public void setup() {
        users = Arrays.asList(
                new User("id1","name1","ps1", Level.BASIC, UserServiceImpl.MIN_LOGOUT_FOR_SILVER-1,30 , "email"),
                new User("id2","name2","ps2", Level.BASIC, UserServiceImpl.MIN_LOGOUT_FOR_SILVER,22 , "email"),
                new User("id3","name3","ps3", Level.SILVER, 94,UserServiceImpl.MIN_RECOMMAND_FOR_GOLD-1 , "email"),
                new User("id4","name4","ps4", Level.GOLD, 12,11 , "email"),
                new User("id5","name5","ps5", Level.SILVER, 1001,49 , "email"),
                new User("id6","name6","ps6", Level.BASIC, 1,0 , "email")
                );
    }
    
    //@Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for(User user : users) userDao.add(user);
        
        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);
        
        userServiceImpl.upgradeLevels();
        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.GOLD);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
        checkLevel(users.get(5), Level.BASIC);
    }
    
    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if(upgraded) {
         // 업그레이드가 일어났는지 확인
            assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel());
        }else {
            // 업그레이드가 안 일어났는지 확인
            assertEquals(userUpdate.getLevel(), user.getLevel());
        }
    }
    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertEquals(userUpdate.getLevel(), expectedLevel);
    }
    
    @Test
    public void add() {
        userDao.deleteAll();
        
        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);
        
        userServiceImpl.add(userWithLevel);
        userServiceImpl.add(userWithoutLevel);
        
        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());
        
        assertEquals(userWithLevel.getLevel(), userWithLevelRead.getLevel());
        assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC);
    }
    
    @Test
    public void upgradeAllorNothing() throws Exception{
        userServiceImpl.upgradeLevels();
    }
}
