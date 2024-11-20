package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.member.enums.Position;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestApplicationCreateDTO {
    @NotNull(message = "지원 포지션을 입력해주세요.")
    private Position applyPosition;

    @Size(max = 500, message = "신청 메시지는 500자를 초과할 수 없습니다.")
    private String message;
}

