package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.member.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "용병 신청 생성 요청 데이터")
public class GuestApplicationCreateDTO {

    @NotNull(message = "지원 포지션을 입력해주세요.")
    @Schema(description = "신청자가 지원하는 포지션", required = true, example = "FORWARD")
    private Position applyPosition;

    @Size(max = 500, message = "신청 메시지는 500자를 초과할 수 없습니다.")
    @Schema(description = "신청 메시지 (선택 사항)", example = "용병으로 팀에 기여하고 싶습니다!")
    private String message;
}
