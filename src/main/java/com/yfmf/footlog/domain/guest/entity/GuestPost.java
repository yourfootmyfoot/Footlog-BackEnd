package com.yfmf.footlog.domain.guest.entity;

import com.yfmf.footlog.BaseTimeEntity;
import com.yfmf.footlog.domain.guest.enums.PostStatus;
import com.yfmf.footlog.domain.member.enums.Position;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_guest_post")
public class GuestPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;  // 용병 지원자 ID

    @Column(nullable = false)
    private LocalDate availableDate;  // 용병 가능 날짜

    @Column(nullable = false)
    private LocalTime availableStartTime;  // 가능 시작 시간

    @Column(nullable = false)
    private LocalTime availableEndTime;    // 가능 종료 시간

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position preferredPosition;  // 선호 포지션

    @Column(nullable = false)
    private String location;  // 선호 지역

    @Column(length = 500)
    private String description;  // 자기소개 또는 추가 설명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status = PostStatus.AVAILABLE;  // 모집 상태 (AVAILABLE, COMPLETED, EXPIRED)

    @Builder
    public GuestPost(Long memberId, LocalDate availableDate, LocalTime availableStartTime,
                     LocalTime availableEndTime, Position preferredPosition, String location,
                     String description) {
        this.memberId = memberId;
        this.availableDate = availableDate;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.preferredPosition = preferredPosition;
        this.location = location;
        this.description = description;
    }

    public void update(LocalDate availableDate, LocalTime availableStartTime,
                       LocalTime availableEndTime, Position preferredPosition,
                       String location, String description, PostStatus status) {
        if (availableDate != null) this.availableDate = availableDate;
        if (availableStartTime != null) this.availableStartTime = availableStartTime;
        if (availableEndTime != null) this.availableEndTime = availableEndTime;
        if (preferredPosition != null) this.preferredPosition = preferredPosition;
        if (location != null) this.location = location;
        if (description != null) this.description = description;
        if (status != null) {
            validateStatusTransition(status);
            this.status = status;
        }
    }

    private void validateStatusTransition(PostStatus newStatus) {
        // 현재 COMPLETED 상태에서는 상태 변경 불가
        if (this.status == PostStatus.COMPLETED) {
            throw new IllegalStateException("완료된 게시글의 상태를 변경할 수 없습니다.");
        }

        // EXPIRED 상태에서는 AVAILABLE로만 변경 가능
        if (this.status == PostStatus.EXPIRED && newStatus != PostStatus.AVAILABLE) {
            throw new IllegalStateException("만료된 게시글은 AVAILABLE 상태로만 변경 가능합니다.");
        }
    }
}
