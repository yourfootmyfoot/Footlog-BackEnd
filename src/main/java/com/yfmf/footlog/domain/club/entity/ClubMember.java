package com.yfmf.footlog.domain.club.entity;

import com.yfmf.footlog.domain.member.domain.Member;
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
    private Long id;

    // Club 객체 대신 clubId 필드로 저장
    @Column(name = "CLUB_ID")
    private Long clubId;

    // Member 객체 대신 memberId 필드로 저장
    @Column(name = "MEMBER_ID")
    private Long memberId;

    public ClubMember() {
    }

    public ClubMember(Long clubId, Long memberId) {
        this.clubId = clubId;
        this.memberId = memberId;
    }
}