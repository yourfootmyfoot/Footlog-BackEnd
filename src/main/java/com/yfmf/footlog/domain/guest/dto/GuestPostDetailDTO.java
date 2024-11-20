package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.guest.entity.GuestPost;
import com.yfmf.footlog.domain.guest.enums.PostStatus;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestPostDetailDTO {
    private Long id;
    private MemberDetailDTO member;  // 더 자세한 회원 정보를 포함하는 DTO
    private LocalDate availableDate;
    private LocalTime availableStartTime;
    private LocalTime availableEndTime;
    private Position preferredPosition;
    private String location;
    private String description;
    private PostStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedDate;

    // 추가 상세 정보
    private List<Position> alternativePositions;  // 대체 가능 포지션들
    private Integer preferredPay;  // 선호 용병비
    private String preferredArea;  // 선호 지역
    private String experience;     // 경력 사항
    private List<String> availableDays;  // 가능한 요일들

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

        // 추가 정보들은 Member 엔티티나 다른 관련 엔티티에서 가져와야 함
        this.alternativePositions = member.getAlternativePositions();
        this.preferredPay = member.getPreferredPay();
        this.preferredArea = member.getPreferredArea();
        this.experience = member.getExperience();
        this.availableDays = Arrays.asList(member.getAvailableDays().split(","));
    }
}