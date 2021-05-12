package com.toby.spring.service;

import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import com.toby.spring.dao.UserDao;
import com.toby.spring.domain.Level;
import com.toby.spring.domain.User;

public class UserServiceImpl implements UserService{
    private UserDao userDao;
    private MailSender mailSender;
    
    public static final int MIN_LOGOUT_FOR_SILVER = 50;
    public static final int MIN_RECOMMAND_FOR_GOLD = 30;
    
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setMailSender(MailSender mailSender) {
        //this.mailSender = mailSender;
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
        upgradeInternal();
    }
    
    private void upgradeInternal() {
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
    
    public void upgradeLevelss(User user) {
        if(canUpgragable(user)) {
            upgradeLevel(user);
        }
    }
    
    private void sendUpgradeEMail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("admin@google.com");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("안내");
        
        //this.mailSender.send(mailMessage);
    }

    @Override
    public void addAndUpdate(User user) {
        // TODO Auto-generated method stub
        
    }
}
