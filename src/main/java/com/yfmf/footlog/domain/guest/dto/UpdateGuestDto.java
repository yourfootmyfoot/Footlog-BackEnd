package com.yfmf.footlog.guest.dto;

import com.yfmf.footlog.enums.MainFoot;
import com.yfmf.footlog.enums.Position;
import jakarta.validation.constraints.NotBlank;
 import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UpdateGuestDto {

    @NotNull(message = "ID cannot be null")
    private Long id;

    /* 업데이트는 선택적으로, 검증 필요없음 */
    @NotBlank(message = "Name cannot be blank or null")
    @Size(min = 1, max = 20, message = "이름은 1글자보다 길어야합니다.")
    private String name;

    private Boolean isAvailable;

    private LocalDateTime createdAt;


}
