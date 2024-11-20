package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.guest.entity.GuestPost;
import com.yfmf.footlog.domain.guest.enums.PostStatus;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.dto.MemberDetailDTO;
import com.yfmf.footlog.domain.member.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "용병 지원글 상세 응답 데이터")
public class GuestPostDetailDTO {

    @Schema(description = "지원글 ID", example = "1")
    private Long id;

    @Schema(description = "작성자 상세 정보")
    private MemberDetailDTO member;  // 더 자세한 회원 정보를 포함하는 DTO

    @Schema(description = "활동 가능 날짜", example = "2024-11-20")
    private LocalDate availableDate;

    @Schema(description = "활동 가능 시작 시간", example = "14:00")
    private LocalTime availableStartTime;

    @Schema(description = "활동 가능 종료 시간", example = "16:00")
    private LocalTime availableEndTime;

    @Schema(description = "선호 포지션", example = "GOALKEEPER")
    private Position preferredPosition;

    @Schema(description = "활동 지역", example = "서울시 강남구")
    private String location;

    @Schema(description = "추가 설명", example = "팀워크와 열정을 중요시합니다.")
    private String description;

    @Schema(description = "지원글 상태", example = "ACTIVE")
    private PostStatus status;

    @Schema(description = "작성 날짜 및 시간", example = "2024-11-19T14:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "수정 날짜 및 시간", example = "2024-11-20T16:30:00")
    private LocalDateTime updatedDate;

    public GuestPostDetailDTO(GuestPost post, Member member) {
        this.id = post.getId();
        this.member = new MemberDetailDTO(member);
        this.availableDate = post.getAvailableDate();
        this.availableStartTime = post.getAvailableStartTime();
        this.availableEndTime = post.getAvailableEndTime();
        this.preferredPosition = post.getPreferredPosition();
        this.location = post.getLocation();
        this.description = post.getDescription();
        this.status = post.getStatus();
        this.createdAt = post.getCreatedAt();
        this.updatedDate = post.getUpdatedDate();
    }
}