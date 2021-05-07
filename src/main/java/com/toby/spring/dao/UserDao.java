package com.toby.spring.dao;

import java.util.List;

import com.toby.spring.domain.User;

    //JDBC�� ����ϴ� �޼ҵ�� SQLException�� ���������� �ʾƵ� JDBC�ʿ��� �˾Ƽ� ����ó���� �����ش�.
    //SQLException�� ������ ����, �ڵ� �󿡼� ó���� �� �ִ� �κ��� 99% ����.
    public interface UserDao {
        public void add(User user);
        public User get(String id);
        public List<User> getAll();
        public void deleteAll();
        public int getCount();
        public void update(User user);
    }
