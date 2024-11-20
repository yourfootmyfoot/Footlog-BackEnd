package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.member.enums.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestPostCreateDTO {
    @NotNull(message = "가능 날짜를 입력해주세요.")
    private LocalDate availableDate;

    @NotNull(message = "시작 시간을 입력해주세요.")
    private LocalTime availableStartTime;

    @NotNull(message = "종료 시간을 입력해주세요.")
    private LocalTime availableEndTime;

    @NotNull(message = "선호 포지션을 입력해주세요.")
    private Position preferredPosition;

    @NotBlank(message = "활동 지역을 입력해주세요.")
    private String location;

    private String description;
}
