package com.yfmf.footlog.domain.guest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_guest")
public class Guest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;  // 회원 ID를 참조하지만 연관관계는 맺지 않습니다.

    @Column(nullable = false)
    private String name;  // 게스트 이름 (회원 이름과 다를 수 있음)

    @Column(nullable = false)
    private Boolean isAvailable;

    private LocalDateTime createdAt;

}
