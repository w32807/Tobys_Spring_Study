package com.toby.spring.dao;

import java.util.List;

import com.toby.spring.domain.User;

//JDBC를 사용하는 메소드는 SQLException을 선언해주지 않아도 JDBC쪽에서 알아서 예외처리를 던져준다.
// SQLException은 원인이 많고, 코드 상에서 처리할 수 있는 부분이 99% 없다.
public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();
}
