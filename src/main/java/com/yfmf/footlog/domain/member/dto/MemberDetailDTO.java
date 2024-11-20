package com.yfmf.footlog.domain.member.dto;

import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.domain.Stat;
import com.yfmf.footlog.domain.member.enums.MainFoot;
import com.yfmf.footlog.domain.member.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailDTO {
    private Long id;
    private String name;
    private Position mainPosition;
    private MainFoot mainFoot;
    private Integer age;
    private String phone;
    private String email;
    private LocalDate birth;
    private String introduction;
    private String profileImageUrl;
    private Stat stat;  // 선수 스탯 정보

    public MemberDetailDTO(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.mainPosition = member.getPosition();
        this.mainFoot = member.getMainFoot();
        this.age = calculateAge(member.getBirth());
        this.phone = member.getPhoneNumber();
        this.email = member.getEmail();
        this.birth = member.getBirth();
        this.introduction = member.getIntroduction();
        this.profileImageUrl = member.getProfileImageUrl();
        this.stat = member.getStat();
    }

    private int calculateAge(LocalDate birth) {
        if (birth == null) return 0;
        return Period.between(birth, LocalDate.now()).getYears();
    }
}