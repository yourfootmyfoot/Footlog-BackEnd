package com.yfmf.footlog.domain.guest.entity;

import com.yfmf.footlog.BaseTimeEntity;
import com.yfmf.footlog.domain.guest.enums.ApplicationStatus;
import com.yfmf.footlog.domain.member.enums.Position;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_guest_application")
public class GuestApplication extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private GuestRecruitment recruitment;  // 용병 모집글

    @Column(nullable = false)
    private Long applicantId;  // 신청자 ID

    @Enumerated(EnumType.STRING)
    private Position applyPosition;  // 지원 포지션

    @Column(length = 500)
    private String message;  // 신청 메시지

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;  // 신청 상태 (PENDING, APPROVED, REJECTED)

    @Builder
    public GuestApplication(GuestRecruitment recruitment, Long applicantId,
                            Position applyPosition, String message) {
        this.recruitment = recruitment;
        this.applicantId = applicantId;
        this.applyPosition = applyPosition;
        this.message = message;
    }

    public void updateStatus(ApplicationStatus newStatus) {
        validateStatusTransition(newStatus);
        this.status = newStatus;
    }

    private void validateStatusTransition(ApplicationStatus newStatus) {
        // 이미 처리된 신청(승인/거절)은 상태 변경 불가
        if (this.status != ApplicationStatus.PENDING) {
            throw new IllegalStateException("이미 처리된 신청의 상태를 변경할 수 없습니다.");
        }

        // PENDING 상태에서만 APPROVED 또는 REJECTED로 변경 가능
        if (newStatus == ApplicationStatus.PENDING) {
            throw new IllegalStateException("이미 대기 상태입니다.");
        }
    }
}
