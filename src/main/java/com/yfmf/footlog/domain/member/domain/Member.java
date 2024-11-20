package com.yfmf.footlog.domain.member.domain;

import com.yfmf.footlog.BaseTimeEntity;
import com.yfmf.footlog.domain.member.dto.MemberUpdateRequestDTO;
import com.yfmf.footlog.domain.member.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_member")
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기본 회원 정보
    @Column(length = 20, nullable = false)
    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
    private String name;

    @Column(length = 100, nullable = false, unique = true)
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}$",
            message = "비밀번호는 4자 이상의 영문자와 숫자 조합이어야 합니다.")
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    private SocialType socialType;

    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'ROLE_USER'")
    private Authority authority;

    @Column
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private MainFoot mainFoot;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(length = 500)
    @Size(max = 500, message = "자기소개는 500자 이하로 작성해주세요.")
    private String introduction;

    private String profileImageUrl;

    private String phoneNumber;

    @Embedded
    private Stat stat;

    @Embedded
    private Record record;

    public Member(Long id, String name, String email, String password, Gender gender, SocialType socialType,
                  Authority authority, LocalDate birth, MainFoot mainFoot, Area area, Position position,
                  String introduction, String profileImageUrl,
                  String phoneNumber, Stat stat, Record record) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.socialType = socialType;
        this.authority = authority;
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

    @Builder
    public Member(LocalDate birth, MainFoot mainFoot, Area area, Position position,
                  String introduction, String profileImageUrl,
                  String phoneNumber, Stat stat, Record record) {
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

    // 회원 정보 수정 메서드
    public void updateProfile(MemberUpdateRequestDTO updateDto) {
        if (updateDto.getName() != null) this.name = updateDto.getName();
        if (updateDto.getBirth() != null) this.birth = updateDto.getBirth();
        if (updateDto.getMainFoot() != null) this.mainFoot = updateDto.getMainFoot();
        if (updateDto.getArea() != null) this.area = updateDto.getArea();
        if (updateDto.getPosition() != null) this.position = updateDto.getPosition();
        if (updateDto.getIntroduction() != null) this.introduction = updateDto.getIntroduction();
        if (updateDto.getProfileImageUrl() != null) this.profileImageUrl = updateDto.getProfileImageUrl();
        if (updateDto.getPhoneNumber() != null) this.phoneNumber = updateDto.getPhoneNumber();
    }

    // 비밀번호 변경 메서드
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    // 능력치 수정 메서드
    public void updateStat(Stat newStat) {
        this.stat = newStat;
    }

    // 기록 수정 메서드
    public void updateRecord(Record newRecord) {
        this.record = newRecord;
    }
}
