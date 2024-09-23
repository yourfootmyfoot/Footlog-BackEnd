package com.yfmf.footlog.domain.user.entity;

import com.nimbusds.openid.connect.sdk.claims.Gender;
import com.yfmf.footlog.BaseTimeEntity;
import com.yfmf.footlog.domain.user.dto.UserUpdateRequestDto;
import com.yfmf.footlog.domain.user.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
@Table(name = "tbl_user") // 테이블 이름은 필요에 따라 설정
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 100)
    private String password;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Embedded
    private Stat stat;

    @Embedded
    private Record record;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Authority authority;

    @Builder
    public User(Long id, String email, String name, String password, LocalDate birth, MainFoot mainFoot, Area area, Position position, String introduction, Boolean isPro, Double height, Double weight, String profileImageUrl, String phoneNumber, UserRole role, Stat stat, Record record, Gender gender, SocialType socialType, Authority authority) {
        this.id = id;
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

    public void update(UserUpdateRequestDto requestDto) {
        this.name = requestDto.getName();
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
