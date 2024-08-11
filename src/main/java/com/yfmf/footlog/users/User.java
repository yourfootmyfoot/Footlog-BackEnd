package com.yfmf.footlog.users;

import com.yfmf.footlog.enums.Area;
import com.yfmf.footlog.enums.MainFoot;
import com.yfmf.footlog.enums.Position;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    private Long kakaoId;

    private String userName;

    private LocalDate birth;

    private MainFoot mainFoot;

    private Area area;

    private Position position;

    private String introduction;

    private Boolean isPro;

    private Double height;

    private Double weight;

    private String profileImageUrl;

    private String phoneNumber;

    private UserRole role;

    @Embedded
    private Stat stat;

    @Embedded
    private Record record;

}
