package com.yfmf.footlog.domain.user.dto;

import com.yfmf.footlog.domain.user.entity.Record;
import com.yfmf.footlog.domain.user.entity.Stat;
import com.yfmf.footlog.domain.user.enums.Position;
import com.yfmf.footlog.domain.user.enums.UserRole;
import com.yfmf.footlog.domain.user.enums.Area;
import com.yfmf.footlog.domain.user.enums.MainFoot;
import com.yfmf.footlog.domain.user.entity.User;
import com.yfmf.footlog.domain.member.enums.Gender;
import com.yfmf.footlog.domain.member.enums.SocialType;
import com.yfmf.footlog.domain.member.enums.Authority;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserSaveRequestDto {

    private String email;  // email 필드로 변경
    private String name;
    private String password;
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
    private UserRole role;
    private Stat stat;
    private Record record;
    private Gender gender;
    private SocialType socialType;
    private Authority authority;

    @Builder
    public UserSaveRequestDto(String email, String name, String password, LocalDate birth, MainFoot mainFoot, Area area, Position position, String introduction, Boolean isPro, Double height, Double weight, String profileImageUrl, String phoneNumber, UserRole role, Stat stat, Record record, Gender gender, SocialType socialType, Authority authority) {
        this.email = email;
        this.name = name;
        this.password = password;
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
        this.role = role;
        this.stat = stat;
        this.record = record;
        this.gender = gender;
        this.socialType = socialType;
        this.authority = authority;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
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
                .role(role)
                .stat(stat)
                .record(record)
                .gender(gender)
                .socialType(socialType)
                .authority(authority)
                .build();
    }
}
