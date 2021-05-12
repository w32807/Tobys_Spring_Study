package com.toby.spring.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import com.toby.spring.domain.User;

public class UserServiceTx implements UserService{
    
    UserService userService;
    // UserServiceImpl에 직접 트랜잭션 메소드를 구현한 로직을, 분리하기 위해서 아래와 같이 설계 
    PlatformTransactionManager transactionManager;
    
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void add(User user) {
        userService.add(user);
    }
    
    @Override
    public void addAndUpdate(User user) {
        userService.addAndUpdate(user);
    }
    
    @Override
    public void upgradeLevels() {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionAttribute());
        
        try {
            userService.upgradeLevels();
            this.transactionManager.commit(status);
        } catch (RuntimeException e) {
            this.transactionManager.rollback(status);
            throw e;
        }
        
    }
}
