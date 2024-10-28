package com.yfmf.footlog.domain.club.entity;

import com.yfmf.footlog.domain.club.enums.JoinRequestStatus;
import com.yfmf.footlog.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Enumerated(EnumType.STRING)
    private JoinRequestStatus status = JoinRequestStatus.PENDING; // 기본값 설정

    public JoinRequest(Club club, Member member) {
        this.club = club;
        this.member = member;
    }
}
