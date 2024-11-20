package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.guest.entity.GuestPost;
import com.yfmf.footlog.domain.guest.enums.PostStatus;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.dto.MemberSimpleDTO;
import com.yfmf.footlog.domain.member.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestPostResponseDTO {
    private Long id;
    private MemberSimpleDTO member;
    private LocalDate availableDate;
    private LocalTime availableStartTime;
    private LocalTime availableEndTime;
    private Position preferredPosition;
    private String location;
    private String description;
    private PostStatus status;
    private LocalDateTime createdAt;

    public GuestPostResponseDTO(GuestPost post, Member member) {
        this.id = post.getId();
        this.member = new MemberSimpleDTO(member);
        this.availableDate = post.getAvailableDate();
        this.availableStartTime = post.getAvailableStartTime();
        this.availableEndTime = post.getAvailableEndTime();
        this.preferredPosition = post.getPreferredPosition();
        this.location = post.getLocation();
        this.description = post.getDescription();
        this.status = post.getStatus();
        this.createdAt = post.getCreatedAt();
    }
}
