package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.guest.entity.Guest;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class GuestSaveRequestDto {

    @NotNull(message = "회원 ID는 필수입니다.")
    private Long memberId;

    @NotBlank(message = "위치는 필수입니다.")
    private String location;

    @NotNull(message = "나이는 필수입니다.")
    @Min(value = 18, message = "나이는 18세 이상이어야 합니다.")
    @Max(value = 100, message = "나이는 100세 이하여야 합니다.")
    private Integer age;

    @NotNull(message = "스케줄 날짜는 필수입니다.")
    @FutureOrPresent(message = "스케줄 날짜는 현재 또는 미래 날짜여야 합니다.")
    private LocalDateTime scheduleDate;

    @NotNull(message = "시작 시간은 필수입니다.")
    private LocalTime scheduleStartTime;

    @NotNull(message = "종료 시간은 필수입니다.")
    private LocalTime scheduleEndTime;

    private String specialRequests;

    @Builder
    public GuestSaveRequestDto(Long memberId, String location, Integer age, LocalDateTime scheduleDate,
                               LocalTime scheduleStartTime, LocalTime scheduleEndTime, String specialRequests) {
        this.memberId = memberId;
        this.location = location;
        this.age = age;
        this.scheduleDate = scheduleDate;
        this.scheduleStartTime = scheduleStartTime;
        this.scheduleEndTime = scheduleEndTime;
        this.specialRequests = specialRequests;
    }

    public Guest toEntity() {
        return Guest.builder()
                .memberId(memberId)
                .location(location)
                .age(age)
                .scheduleDate(scheduleDate)
                .scheduleDay(scheduleDate.getDayOfWeek())
                .scheduleStartTime(scheduleStartTime)
                .scheduleEndTime(scheduleEndTime)
                .specialRequests(specialRequests)
                .build();
    }
}