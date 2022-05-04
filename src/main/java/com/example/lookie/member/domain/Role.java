package com.example.lookie.member.domain;


public enum Role {
    ROLE_ADMIN("동아리 생성자"),
    ROLE_USER("동아리 가입자");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
