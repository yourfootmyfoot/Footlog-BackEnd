package com.yfmf.footlog.domain.member.dto;

import com.yfmf.footlog.domain.member.domain.*;
import com.yfmf.footlog.domain.member.domain.Record;
import com.yfmf.footlog.domain.member.enums.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MemberSaveRequestDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Gender gender;
    private SocialType socialType;
    private Authority authority;
    private LocalDate birth;
    private MainFoot mainFoot;
    private Area area;
    private Position position;
    private String introduction;
    private String profileImageUrl;
    private String phoneNumber;
    private Stat stat;
    private Record record;

    @Builder
    public MemberSaveRequestDTO(Long id, String name, LocalDate birth, MainFoot mainFoot, Area area, Position position, String introduction, String profileImageUrl, String phoneNumber, Stat stat, Record record) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.mainFoot = mainFoot;
        this.area = area;
        this.position = position;
        this.introduction = introduction;
        this.profileImageUrl = profileImageUrl;
        this.phoneNumber = phoneNumber;
        this.stat = stat;
        this.record = record;
    }

    public Member toEntity() {
        return Member.builder()
                .birth(birth)
                .mainFoot(mainFoot)
                .area(area)
                .position(position)
                .introduction(introduction)
                .profileImageUrl(profileImageUrl)
                .phoneNumber(phoneNumber)
                .stat(stat)
                .record(record)
                .build();
    }
}
