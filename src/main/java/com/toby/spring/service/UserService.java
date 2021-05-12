package com.toby.spring.service;

import com.toby.spring.domain.User;

public interface UserService {
    public void upgradeLevels();
    public void add(User user);
    public void addAndUpdate(User user);
    
}
