package com.yfmf.footlog.users.dto;

import com.yfmf.footlog.enums.Area;
import com.yfmf.footlog.enums.MainFoot;
import com.yfmf.footlog.enums.Position;
import com.yfmf.footlog.users.UserRole;
import com.yfmf.footlog.users.entity.Record;
import com.yfmf.footlog.users.entity.Stat;
import com.yfmf.footlog.users.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserSaveRequestDto {

    private Long kakaoId;
    private String userName;
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

    @Builder
    public UserSaveRequestDto(Long kakaoId, String userName, LocalDate birth, MainFoot mainFoot, Area area, Position position, String introduction, Boolean isPro, Double height, Double weight, String profileImageUrl, String phoneNumber, UserRole role, Stat stat, Record record) {
        this.kakaoId = kakaoId;
        this.userName = userName;
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
    }

    public User toEntity() {
        return User.builder()
                .kakaoId(kakaoId)
                .userName(userName)
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
                .build();
    }
}
