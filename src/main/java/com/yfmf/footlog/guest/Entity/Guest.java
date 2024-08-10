package com.yfmf.footlog.guest.Entity;

import com.yfmf.footlog.enums.MainFoot;
import com.yfmf.footlog.enums.Position;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "guest")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;


    @Enumerated(EnumType.STRING)
    @Column(name = "mainFoot")
    private MainFoot mainFoot;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @Column(name = "isAvailable")
    private boolean isAvailable; // 현재 활동 가능 여부

    // Guest 관련 도메인 로직
    public void becomeAvailable() {
        this.isAvailable = true;
    }

    public void becomeUnavailable() {
        this.isAvailable = false;
    }

}
