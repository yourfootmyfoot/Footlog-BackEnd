package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.guest.entity.GuestRecruitment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestRecruitmentDetailDTO extends GuestRecruitmentResponseDTO {
    private List<GuestApplicationResponseDTO> applications;  // 신청자 목록

    public GuestRecruitmentDetailDTO(GuestRecruitment recruitment, Club club,
                                     List<GuestApplicationResponseDTO> applications) {
        super(recruitment, club);
        this.applications = applications;
    }
}
