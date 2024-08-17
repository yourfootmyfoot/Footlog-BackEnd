package com.yfmf.footlog.users.entity;

import com.yfmf.footlog.enums.Area;
import com.yfmf.footlog.enums.MainFoot;
import com.yfmf.footlog.enums.Position;
import com.yfmf.footlog.users.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    @Column(nullable = false)
    private Long kakaoId;

    @Column(nullable = false)
    private String userName;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private MainFoot mainFoot;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Enumerated(EnumType.STRING)
    private Position position;

    private String introduction;

    private Boolean isPro;

    private Double height;

    private Double weight;

    private String profileImageUrl;

    private String phoneNumber;

    @Column(nullable = false)
    private UserRole role;

    @Embedded
    private Stat stat;

    @Embedded
    private Record record;

}
