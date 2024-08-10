package com.yfmf.footlog.guest.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "guest")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String name;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private MainFoot mainFoot;

    @Enumerated(EnumType.STRING)
    private Position position;

    private String contactInfo;

    private boolean isAvailable; // 현재 활동 가능 여부

    // Guest 관련 도메인 로직
    public void becomeAvailable() {
        this.isAvailable = true;
    }

    public void becomeUnavailable() {
        this.isAvailable = false;
    }



}
