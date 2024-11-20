package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.member.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "용병 지원글 생성 요청 데이터")
public class GuestPostCreateDTO {

    @NotNull(message = "가능 날짜를 입력해주세요.")
    @Schema(description = "활동 가능 날짜", example = "2024-11-20")
    private LocalDate availableDate;

    @NotNull(message = "시작 시간을 입력해주세요.")
    @Schema(description = "활동 가능 시작 시간", example = "14:00")
    private LocalTime availableStartTime;

    @NotNull(message = "종료 시간을 입력해주세요.")
    @Schema(description = "활동 가능 종료 시간", example = "16:00")
    private LocalTime availableEndTime;

    @NotNull(message = "선호 포지션을 입력해주세요.")
    @Schema(description = "선호 포지션", example = "GOALKEEPER")
    private Position preferredPosition;

    @NotBlank(message = "활동 지역을 입력해주세요.")
    @Schema(description = "활동 지역", example = "서울시 강남구")
    private String location;

    @Schema(description = "추가 설명", example = "저는 팀워크와 열정을 중요하게 생각합니다!")
    private String description;
}
