package com.toby.spring.domain;

import static org.junit.Assert.assertEquals;

import org.junit.*;

public class UserTest {
    User user;
    
    @Before
    public void setup() {
        user = new User();
    }
    
    @Test()
    public void upgradeLevel() {
        Level[] levels = Level.values(); // enum의 기본 메소드로 values들을 가져옴
        for(Level level : levels) {
            System.out.println(level);
            if(level.nextLevel() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertEquals(user.getLevel(), level.nextLevel());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void cannotUpgradeLevel() {
        Level[] levels = Level.values(); // enum의 기본 메소드로 values들을 가져옴
        System.out.println(levels);
        for(Level level : levels) {
            if(level.nextLevel() != null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertEquals(user.getLevel(), level.nextLevel());
        }
    }
}
