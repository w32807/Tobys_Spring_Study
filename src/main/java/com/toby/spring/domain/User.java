package com.toby.spring.domain;

public class User {

    String id;
    String name;
    String password;
    String email;
    
    // 서비스 추상화 하면서 추가
    Level level;
    int login;
    int recommand;
    
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRecommand() {
        return recommand;
    }

    public void setRecommand(int recommand) {
        this.recommand = recommand;
    }

    public User(String id, String name, String password, Level level, int login, int recommand, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommand = recommand;
        this.email = email;
    }
    
    public User() {}
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    // Level 업그레이드는 Level을 가지고 있는 User 오브젝트에서 처리하고, 서비스에서는 호출만!
    public void upgradeLevel() {
        Level nextLevel = this.level.nextLevel();
        if(nextLevel == null) {
            throw new IllegalArgumentException(this.level + "은 업그레이드가 불가능합니다.");
        }else {
            this.level = nextLevel;
        }
    }
}
