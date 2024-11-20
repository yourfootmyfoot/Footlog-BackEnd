package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.guest.entity.GuestRecruitment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "용병 모집 상세 정보 DTO")
public class GuestRecruitmentDetailDTO extends GuestRecruitmentResponseDTO {

    @Schema(description = "모집글에 대한 신청자 목록", required = true)
    private List<GuestApplicationResponseDTO> applications;

    public GuestRecruitmentDetailDTO(GuestRecruitment recruitment, Club club,
                                     List<GuestApplicationResponseDTO> applications) {
        super(recruitment, club);
        this.applications = applications;
    }
}
