package com.yfmf.footlog.domain.member.dto;

import com.yfmf.footlog.domain.member.domain.Record;
import com.yfmf.footlog.domain.member.domain.Stat;
import com.yfmf.footlog.domain.member.domain.UserRole;
import com.yfmf.footlog.domain.member.enums.Area;
import com.yfmf.footlog.domain.member.enums.MainFoot;
import com.yfmf.footlog.domain.member.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDto {

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
}
