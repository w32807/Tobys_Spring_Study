package com.toby.spring.dao;

import java.util.List;

import com.toby.spring.domain.User;

//JDBC�� ����ϴ� �޼ҵ�� SQLException�� ���������� �ʾƵ� JDBC�ʿ��� �˾Ƽ� ����ó���� �����ش�.
// SQLException�� ������ ����, �ڵ� �󿡼� ó���� �� �ִ� �κ��� 99% ����.
public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();
}
