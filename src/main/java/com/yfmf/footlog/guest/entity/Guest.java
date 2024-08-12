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
@AllArgsConstructor
@ToString
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private MainFoot mainFoot;

    @Enumerated(EnumType.STRING)
    private Position position;

    private Boolean isAvailable; // 현재 활동 가능 여부

}
