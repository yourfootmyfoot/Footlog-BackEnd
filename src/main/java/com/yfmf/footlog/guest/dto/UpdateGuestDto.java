package com.yfmf.footlog.guest.dto;

import com.yfmf.footlog.enums.MainFoot;
import com.yfmf.footlog.enums.Position;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class UpdateGuestDto {

    @NotNull(message = "ID cannot be null")
    private Long id;

    /* 업데이트는 선택적으로, 검증 필요없음 */
    private String name;

    private MainFoot mainFoot;

    private Position position;

    private boolean isAvailable;
}
