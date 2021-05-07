package com.toby.spring.domain;

// 사용자 등급을 정하는 enum 타입.
// 정수를 사용하더라도, IDE 수준에서 컴파일이 가능하다.
public enum Level {
    GOLD(3, null),
    SILVER(2, GOLD),
    BASIC(1, SILVER);
    
    private final int value;
    private final Level next;
    
    private Level(int value, Level next) { 
        this.value = value;
        this.next = next;
    }
    
    public int intValue() {
        return this.value;
    }
    public Level nextLevel() {
        return this.next;
    }
    
    public static Level valueOf(int value) {
        switch (value) {
        case 1: return BASIC;
        case 2: return SILVER;
        case 3: return GOLD;
        default: throw new AssertionError("Unknown value : " + value);
        }
    }
}
