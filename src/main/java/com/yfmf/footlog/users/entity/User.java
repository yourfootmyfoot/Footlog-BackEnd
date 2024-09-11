package com.yfmf.footlog.users.entity;

import com.yfmf.footlog.enums.Area;
import com.yfmf.footlog.enums.MainFoot;
import com.yfmf.footlog.enums.Position;
import com.yfmf.footlog.users.UserRole;
import com.yfmf.footlog.users.dto.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    private String socialId;

    @Column(nullable = false)
    private String userName;

    private String email;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private MainFoot mainFoot;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Enumerated(EnumType.STRING)
    private Position position;

    private String introduction;

    private Boolean isPro;

    private Double height;

    private Double weight;

    private String profileImageUrl;

    private String phoneNumber;

//    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Embedded
    private Stat stat;

    @Embedded
    private Record record;

    @Builder
    public User(String socialId, String userName, UserRole role) {
        this.socialId = socialId;
        this.userName = userName;
        this.role = role;
    }

    @Builder
    public User(Long userId, String socialId, String userName, String email, LocalDate birth, MainFoot mainFoot, Area area, Position position, String introduction, Boolean isPro, Double height, Double weight, String profileImageUrl, String phoneNumber, UserRole role, Stat stat, Record record) {
        this.userId = userId;
        this.socialId = socialId;
        this.userName = userName;
        this.email = email;
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

    public void update(UserUpdateRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.email = requestDto.getEmail();
        this.birth = requestDto.getBirth();
        this.mainFoot = requestDto.getMainFoot();
        this.area = requestDto.getArea();
        this.position = requestDto.getPosition();
        this.introduction = requestDto.getIntroduction();
        this.isPro = requestDto.getIsPro();
        this.height = requestDto.getHeight();
        this.weight = requestDto.getWeight();
        this.profileImageUrl = requestDto.getProfileImageUrl();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.role = requestDto.getRole();
        this.stat = requestDto.getStat();
        this.record = requestDto.getRecord();
    }
}
