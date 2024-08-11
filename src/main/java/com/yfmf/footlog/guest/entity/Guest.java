package com.yfmf.footlog.guest.entity;

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

    public Guest() {
    }


    public Guest(String name, LocalDateTime createdAt, MainFoot mainFoot, Position position, boolean isAvailable) {
        this.name = name;
        this.createdAt = createdAt;
        this.mainFoot = mainFoot;
        this.position = position;
        this.isAvailable = isAvailable;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setMainFoot(MainFoot mainFoot) {
        this.mainFoot = mainFoot;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public MainFoot getMainFoot() {
        return mainFoot;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Guest 관련 도메인 로직
    public void becomeAvailable() {
        this.isAvailable = true;
    }

    public void becomeUnavailable() {
        this.isAvailable = false;
    }

}
