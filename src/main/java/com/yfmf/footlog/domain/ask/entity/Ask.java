package com.yfmf.footlog.domain.ask.entity;

import com.yfmf.footlog.BaseTimeEntity;
import com.yfmf.footlog.domain.ask.dto.AskResponseDto;
import com.yfmf.footlog.domain.ask.enums.AskCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_ask")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Ask extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASK_ID")
    private Long id;

    private Long userId; // 작성 유저 ID
    private AskCategory category; // 질문 카테고리
    private String title;
    private String content;
    private Boolean answered; // 답변 여부

    @Builder
    public Ask(Long userId, AskCategory category, String title, String content) {
        this.userId = userId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.answered = false;
    }

    public AskResponseDto toResponseDto() {
        return AskResponseDto.builder()
                .userId(userId)
                .category(category)
                .title(title)
                .content(content)
                .answered(answered)
                .build();
    }
}
