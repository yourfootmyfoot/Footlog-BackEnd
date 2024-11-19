package com.yfmf.footlog.domain.ask.dto;

import com.yfmf.footlog.domain.ask.enums.AskCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AskResponseDto {

    @NotBlank(message = "문의 ID는 필수입니다.")
    private Long askId;

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

    @NotNull(message = "답변 여부는 필수입니다.")
    private Boolean answered;

    @Builder
    public AskResponseDto(Long askId, Long userId, AskCategory category, String title, String content, Boolean answered) {
        this.askId = askId;
        this.userId = userId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.answered = answered;
    }
}
