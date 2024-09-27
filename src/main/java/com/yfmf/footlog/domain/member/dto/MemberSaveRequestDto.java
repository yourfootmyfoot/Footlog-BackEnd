package com.yfmf.footlog.domain.member.dto;

import com.yfmf.footlog.domain.member.domain.*;
import com.yfmf.footlog.domain.member.domain.Record;
import com.yfmf.footlog.domain.member.enums.Area;
import com.yfmf.footlog.domain.member.enums.MainFoot;
import com.yfmf.footlog.domain.member.enums.Position;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MemberSaveRequestDto {

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
    private Boolean isPro;
    private Double height;
    private Double weight;
    private String profileImageUrl;
    private String phoneNumber;
    private Stat stat;
    private Record record;

    @Builder
    public MemberSaveRequestDto(Long id, String name, LocalDate birth, MainFoot mainFoot, Area area, Position position, String introduction, Boolean isPro, Double height, Double weight, String profileImageUrl, String phoneNumber, UserRole role, Stat stat, Record record) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.mainFoot = mainFoot;
        this.area = area;
        this.position = position;
        this.introduction = introduction;
        this.isPro = isPro;
        this.height = height;
        this.weight = weight;
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
                .isPro(isPro)
                .height(height)
                .weight(weight)
                .profileImageUrl(profileImageUrl)
                .phoneNumber(phoneNumber)
                .stat(stat)
                .record(record)
                .build();
    }
}
