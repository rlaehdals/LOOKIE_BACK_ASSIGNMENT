package com.example.lookie.grouprequest.domain;

public enum RequestStatus {
    ACCEPT("승인 상태"),
    REJECTION("거절 상태"),
    HOLDING("승인 대기 상태");

    private final String description;

    RequestStatus(String description) {
        this.description = description;
    }
}
