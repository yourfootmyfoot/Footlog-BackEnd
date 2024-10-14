package com.yfmf.footlog.domain.guest.dto;


import com.yfmf.footlog.domain.guest.entity.Guest;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class GuestUpdateRequestDto {

    private String name;
    private String location;
    private Integer age;
    private LocalDateTime scheduleDate;
    private LocalTime scheduleStartTime;
    private LocalTime scheduleEndTime;
    private String specialRequests;
    private Boolean available;

    @Builder
    public GuestUpdateRequestDto(String name, String location, Integer age, LocalDateTime scheduleDate,
                                 LocalTime scheduleStartTime, LocalTime scheduleEndTime, String specialRequests) {
        this.name = name;
        this.location = location;
        this.age = age;
        this.scheduleDate = scheduleDate;
        this.scheduleStartTime = scheduleStartTime;
        this.scheduleEndTime = scheduleEndTime;
        this.specialRequests = specialRequests;
    }

    public Guest toEntity() {
        return Guest.builder()
                .location(location)
                .age(age)
                .scheduleDate(scheduleDate)
                .scheduleDay(scheduleDate != null ? scheduleDate.getDayOfWeek() : null)
                .scheduleStartTime(scheduleStartTime)
                .scheduleEndTime(scheduleEndTime)
                .specialRequests(specialRequests)
                .build();
    }
}