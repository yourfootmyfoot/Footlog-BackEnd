package com.yfmf.footlog.domain.user.dto;

import com.yfmf.footlog.domain.user.entity.Record;
import com.yfmf.footlog.domain.user.entity.Stat;
import com.yfmf.footlog.domain.user.enums.MainFoot;
import com.yfmf.footlog.domain.user.enums.Position;
import com.yfmf.footlog.domain.user.enums.UserRole;
import com.yfmf.footlog.domain.user.enums.Area;
import com.yfmf.footlog.domain.member.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {

    private String name;
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
    private Gender gender;  // 추가
}
