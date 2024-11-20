package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.guest.enums.PostStatus;
import com.yfmf.footlog.domain.member.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "용병 지원글 수정 요청 데이터")
public class GuestPostUpdateDTO {

    @Schema(description = "활동 가능 날짜", example = "2024-11-20")
    private LocalDate availableDate;

    @Schema(description = "활동 가능 시작 시간", example = "14:00")
    private LocalTime availableStartTime;

    @Schema(description = "활동 가능 종료 시간", example = "16:00")
    private LocalTime availableEndTime;

    @Schema(description = "선호 포지션", example = "MIDFIELDER")
    private Position preferredPosition;

    @Schema(description = "활동 지역", example = "서울시 강남구")
    private String location;

    @Schema(description = "추가 설명", example = "적극적인 플레이를 선호합니다.")
    private String description;

    @Schema(description = "지원글 상태", example = "ACTIVE")
    private PostStatus status;
}