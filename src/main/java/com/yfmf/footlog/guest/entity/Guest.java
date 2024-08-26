package com.yfmf.footlog.guest.entity;

import com.yfmf.footlog.enums.MainFoot;
import com.yfmf.footlog.enums.Position;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Builder
@AllArgsConstructor
@ToString
public class Guest {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private Boolean isAvailable; // 현재 활동 가능 여부

    @Enumerated(EnumType.STRING)
    private MainFoot mainFoot;

    @Enumerated(EnumType.STRING)
    private Position position;

}
