package com.yfmf.footlog.domain.club.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_club_member")
@Getter
@Setter
public class ClubMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "USER_ID")
    private Long userId;  // 구단원 ID (유저 ID)

    @ManyToOne
    @JoinColumn(name = "CLUB_ID")
    private Club club;  // 구단과의 연관 관계

    public ClubMember() {
    }

    public ClubMember(Long userId, Club club) {
        this.userId = userId;
        this.club = club;
    }
}