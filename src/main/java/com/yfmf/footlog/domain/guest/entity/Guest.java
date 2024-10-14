package com.yfmf.footlog.domain.guest.entity;

import com.yfmf.footlog.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_guest")
public class Guest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private LocalDateTime scheduleDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek scheduleDay;

    @Column(nullable = false)
    private LocalTime scheduleStartTime;

    @Column(nullable = false)
    private LocalTime scheduleEndTime;

    private String specialRequests;

    @Column(nullable = false)
    private boolean available;

    @Builder
    public Guest(Long memberId, String location, Integer age, LocalDateTime scheduleDate, DayOfWeek scheduleDay,
                 LocalTime scheduleStartTime, LocalTime scheduleEndTime, String specialRequests, Boolean available) {
        this.memberId = memberId;
        this.location = location;
        this.age = age;
        this.scheduleDate = scheduleDate;
        this.scheduleDay = scheduleDay;
        this.scheduleStartTime = scheduleStartTime;
        this.scheduleEndTime = scheduleEndTime;
        this.specialRequests = specialRequests;
        this.available = available;
    }

    public void update(String location, Integer age, LocalDateTime scheduleDate, DayOfWeek scheduleDay,
                       LocalTime scheduleStartTime, LocalTime scheduleEndTime, String specialRequests, Boolean available) {
        this.location = location;
        this.age = age;
        this.scheduleDate = scheduleDate;
        this.scheduleDay = scheduleDay;
        this.scheduleStartTime = scheduleStartTime;
        this.scheduleEndTime = scheduleEndTime;
        this.available = available;

    }

    public boolean updateAvailability(boolean available) {
        return available;
    }

}