package com.yfmf.footlog.domain.club.dto;

import com.yfmf.footlog.domain.club.enums.ClubLevel;
import com.yfmf.footlog.domain.club.enums.PeakDays;
import com.yfmf.footlog.domain.club.enums.PeakHours;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ClubRegistResponseDTO {
    @Schema(description = "구단주의 userId", example = "1")
    private Long userId;

    @Schema(description = "구단 이름", example = "FC서울")
    private String clubName;

    @Schema(description = "구단 소개글", example = "안녕하세요. FC서울입니다")
    private String clubIntroduction;

    @Schema(description = "구단 코드", example = "FCSeoul")
    private String clubCode;

//    @Schema(description = "등록 날짜")
//    private LocalDateTime erollDate;

    @Schema(description = "구단원 수")
    private int memberCount;

    @Schema(description = "자주 운동하는 요일", example = "[\"월\", \"화\"]")
    private List<PeakDays> days;

    @Schema(description = "자주 운동하는 시간대", example = "[\"아침\", \"저녁\"]")
    private List<PeakHours> times;

    @Schema(description = "구단 실력", example = "아마추어")
    private ClubLevel clubLevel;

    @Schema(description = "주사용 운동장", example = "울산문수월드컵경기장")
    private String stadiumName;  // 운동하는 경기장 이름

    @Schema(description = "활동 도시", example = "서울")
    private String city;

    @Schema(description = "활동 지역", example = "강남구")
    private String region;

    @Schema(description = "연령대", example = "20대")
    private String ageGroup;

    @Schema(description = "성별", example = "남성")
    private String gender;
    // 기본 생성자
    public ClubRegistResponseDTO() {}

    // 모든 필드를 받는 생성자
    public ClubRegistResponseDTO(Long userId, String clubName, String clubIntroduction, String clubCode,
                                 int memberCount, List<PeakDays> days, List<PeakHours> times,
                                 ClubLevel clubLevel, String stadiumName, String city, String region,
                                 String ageGroup, String gender) {
        this.userId = userId;
        this.clubName = clubName;
        this.clubIntroduction = clubIntroduction;
        this.clubCode = clubCode;
        this.memberCount = memberCount;
        this.days = days;
        this.times = times;
        this.clubLevel = clubLevel;
        this.stadiumName = stadiumName;
        this.city = city;
        this.region = region;
        this.ageGroup = ageGroup;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "ClubRegistResponseDTO{" +
                "userId=" + userId +
                ", clubName='" + clubName + '\'' +
                ", clubIntroduction='" + clubIntroduction + '\'' +
                ", clubCode='" + clubCode + '\'' +
                ", memberCount=" + memberCount +
                ", days=" + days +
                ", times=" + times +
                ", clubLevel='" + clubLevel + '\'' +
                ", stadiumName='" + stadiumName + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", ageGroup='" + ageGroup + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
