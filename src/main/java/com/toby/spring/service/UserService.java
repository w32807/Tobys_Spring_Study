package com.toby.spring.service;

import java.util.List;

import com.toby.spring.dao.UserDao;
import com.toby.spring.domain.Level;
import com.toby.spring.domain.User;

public class UserService {
    UserDao userDao;

    public static final int MIN_LOGOUT_FOR_SILVER = 50;
    public static final int MIN_RECOMMAND_FOR_GOLD = 30;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    /*
    public void upgradeLevelss() {
        List<User> users = userDao.getAll();
        
        for(User user : users) {
            Boolean changed = null;
            
            if(user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
                user.setLevel(Level.SILVER);
                changed = true;
            }else if(user.getLevel() == Level.SILVER && user.getRecommand() >= 30) {
                user.setLevel(Level.GOLD);
                changed = true;
            }else if(user.getLevel() == Level.GOLD){
                changed = false;
            }else {
                changed = false;
            }
            if(changed) userDao.update(user);
        }
    }
    */
    // 각각 1개의 역할만 하도록 메소드를 리팩토링
    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        
        for(User user : users) {
          if(canUpgragable(user)) {
              upgradeLevel(user);
          }
        }
    }
    
    private void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    private boolean canUpgragable(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
        case BASIC:
            return (user.getLogin() >= MIN_LOGOUT_FOR_SILVER);
        case SILVER:
            return (user.getRecommand() >= MIN_RECOMMAND_FOR_GOLD);
        case GOLD:
            return false;
        default: throw new IllegalArgumentException("Unknown Level >> " + currentLevel);
        }
    }

    public void add(User user) {
        if(user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
    
}
