package com.yfmf.footlog.domain.club.dto;

import com.yfmf.footlog.domain.club.entity.Club;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubDetailResponseDTO {
    private Club club;
    private boolean isMember;
    private boolean hasPermission;

    public ClubDetailResponseDTO(Club club, boolean isMember, boolean hasPermission) {
        this.club = club;
        this.isMember = isMember;
        this.hasPermission = hasPermission;
    }

    // 게터와 세터 추가
}
