package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.member.enums.Position;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestRecruitmentCreateDTO {
    @NotNull(message = "구단 ID를 입력해주세요.")
    private Long clubId;

    @NotNull(message = "경기 일시를 입력해주세요.")
    private LocalDateTime matchDateTime;

    @NotBlank(message = "경기 장소를 입력해주세요.")
    private String location;

    @NotNull(message = "필요 인원을 입력해주세요.")
    @Min(value = 1, message = "필요 인원은 1명 이상이어야 합니다.")
    private Integer requiredNumber;

    @NotEmpty(message = "필요 포지션을 입력해주세요.")
    private List<Position> requiredPositions;

    @NotNull(message = "용병비를 입력해주세요.")
    @Min(value = 0, message = "용병비는 0원 이상이어야 합니다.")
    private Integer pay;

    private String description;
}