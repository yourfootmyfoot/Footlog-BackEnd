package com.yfmf.footlog.domain.ask.dto;

import com.yfmf.footlog.domain.ask.entity.Ask;
import com.yfmf.footlog.domain.ask.enums.AskCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AskCreateRequestDto {

    @NotBlank(message = "userId는 필수입니다.")
    private Long userId;

    @NotBlank(message = "문의 카테고리는 필수입니다.")
    private AskCategory category;

    @NotBlank(message = "문의 제목은 필수입니다.")
    @Size(min = 2, max = 40, message = "문의 제목은 2 ~ 40 글자입니다.")
    private String title;

    @NotBlank(message = "문의 내용은 필수입니다.")
    @Size(min = 5, message = "문의 내용은 5글자 이상입니다.")
    private String content;

    public Ask toEntity() {
        return Ask.builder()
                .userId(userId)
                .category(category)
                .title(title)
                .content(content)
                .build();
    }
}
