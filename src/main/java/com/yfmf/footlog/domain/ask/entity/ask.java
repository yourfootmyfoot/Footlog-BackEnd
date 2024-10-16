package com.yfmf.footlog.domain.ask.entity;

import com.yfmf.footlog.BaseTimeEntity;
import com.yfmf.footlog.domain.ask.enums.askCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_ask")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ask extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASK_ID")
    private Long id;

    private Long userId; // 작성 유저 ID
    private askCategory category; // 질문 카테고리
    private String title;
    private String content;

    @Builder
    public ask(Long userId, askCategory category, String title, String content) {
        this.userId = userId;
        this.category = category;
        this.title = title;
        this.content = content;
    }
}
