package com.yfmf.footlog.domain.guest.dto;

import com.yfmf.footlog.domain.guest.enums.PostStatus;
import com.yfmf.footlog.domain.member.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestPostUpdateDTO {
    private LocalDate availableDate;
    private LocalTime availableStartTime;
    private LocalTime availableEndTime;
    private Position preferredPosition;
    private String location;
    private String description;
    private PostStatus status;
}