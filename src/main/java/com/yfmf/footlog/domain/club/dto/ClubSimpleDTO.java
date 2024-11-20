package com.yfmf.footlog.domain.club.dto;

import com.yfmf.footlog.domain.club.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubSimpleDTO {
    private Long id;
    private String name;

    public ClubSimpleDTO(Club club) {
        this.id = club.getClubId();
        this.name = club.getClubName();
    }
}
