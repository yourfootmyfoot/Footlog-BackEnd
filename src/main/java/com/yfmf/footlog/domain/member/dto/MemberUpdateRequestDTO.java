package com.yfmf.footlog.domain.member.dto;

import com.yfmf.footlog.domain.member.domain.Stat;
import com.yfmf.footlog.domain.member.enums.Area;
import com.yfmf.footlog.domain.member.enums.MainFoot;
import com.yfmf.footlog.domain.member.enums.Position;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberUpdateRequestDTO {

    @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
    private String name;

    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private MainFoot mainFoot;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Size(max = 500, message = "자기소개는 500자 이하로 작성해주세요.")
    private String introduction;

    private String profileImageUrl;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
    private String phoneNumber;

    // Stat 업데이트를 위한 내부 클래스
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StatUpdateDto {
        @Min(0) @Max(100)
        private Integer stamina;

        @Min(0) @Max(100)
        private Integer defend;

        @Min(0) @Max(100)
        private Integer speed;

        @Min(0) @Max(100)
        private Integer pass;

        @Min(0) @Max(100)
        private Integer shoot;

        @Min(0) @Max(100)
        private Integer dribble;

        public Stat toEntity() {
            return Stat.builder()
                    .stamina(stamina)
                    .defend(defend)
                    .speed(speed)
                    .pass(pass)
                    .shoot(shoot)
                    .dribble(dribble)
                    .build();
        }
    }
}
