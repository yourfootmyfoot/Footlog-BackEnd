package com.yfmf.footlog.domain.member.dto;

import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSimpleDTO {
    private Long id;
    private String name;
    private Position preferredPosition;  // Member 엔티티에 추가 필요

    public MemberSimpleDTO(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.preferredPosition = member.getPosition();  // Member 엔티티에 추가 필요
    }
}