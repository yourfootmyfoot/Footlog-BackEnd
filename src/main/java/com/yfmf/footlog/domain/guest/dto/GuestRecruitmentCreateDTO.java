package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.member.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "용병 모집글 생성 요청 데이터")
public class GuestRecruitmentCreateDTO {

    @Schema(description = "모집글 제목 ID", example = "같이 축구해요~!", required = true)
    @NotNull(message = "모집글 제목을 입력해주세요.")
    private String title;

    @Schema(description = "작성자 닉네임", example = "송호진", required = true)
    @NotNull(message = "작성자 닉네임를 입력해주세요.")
    private String name;

    @Schema(description = "구단 ID", example = "1", required = true)
    @NotNull(message = "구단 ID를 입력해주세요.")
    private Long clubId;

    @Schema(description = "경기 날짜", example = "2024-12-25", required = true)
    @NotNull(message = "경기 날짜를 입력해주세요.")
    private LocalDate matchDate;

    @Schema(description = "경기 시작 시간", example = "14:00", required = true)
    @NotNull(message = "경기 시작 시간을 입력해주세요.")
    private LocalTime matchStartTime;

    @Schema(description = "경기 종료 시간", example = "16:00", required = true)
    @NotNull(message = "경기 종료 시간을 입력해주세요.")
    private LocalTime matchEndTime;

    @Schema(description = "경기 장소", example = "서울월드컵경기장", required = true)
    @NotBlank(message = "경기 장소를 입력해주세요.")
    private String location;

    @Schema(description = "필요 인원", example = "5", required = true, minimum = "1")
    @NotNull(message = "필요 인원을 입력해주세요.")
    @Min(value = 1, message = "필요 인원은 1명 이상이어야 합니다.")
    private Integer requiredNumber;

    @Schema(description = "필요 포지션 목록", example = "[\"FORWARD\", \"GOALKEEPER\"]", required = true)
    @NotEmpty(message = "필요 포지션을 입력해주세요.")
    private List<Position> requiredPositions;

    @Schema(description = "용병비 (원)", example = "50000", required = true, minimum = "0")
    @NotNull(message = "용병비를 입력해주세요.")
    @Min(value = 0, message = "용병비는 0원 이상이어야 합니다.")
    private Integer pay;

    @Schema(description = "추가 설명", example = "경험 많은 선수를 선호합니다.")
    private String description;
}