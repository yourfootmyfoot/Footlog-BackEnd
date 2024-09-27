package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.member.enums.MainFoot;
import com.yfmf.footlog.domain.member.enums.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateGuestDto {

    @NotBlank(message = "Name cannot be blank or null")
    @Size(min = 1, max = 20, message = "이름은 1글자보다 길어야합니다.")
    private String name;

    @NotNull(message = "Creation date cannot be null")
    private LocalDateTime createdAt;

    private MainFoot mainFoot;

    private Position position;

    private Boolean isAvailable;

}