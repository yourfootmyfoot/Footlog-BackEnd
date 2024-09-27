package com.yfmf.footlog.domain.club.dto;



import com.yfmf.footlog.domain.club.enums.PeakDays;
import com.yfmf.footlog.domain.club.enums.PeakHours;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ClubRegistRequestDTO {

    @Schema(hidden = true)
    private Long userId;

    @Schema(description = "구단 이름", example = "FC서울")
    @NotNull(message = "등록할 구단 이름를 입력해주세요.")
    @Size(min = 2, max = 20, message = "구단 이름은 2자 이상 20자 이하로 입력해야 합니다.")
    private String clubName;

    @Schema(description = "구단 소개글", example = "안녕하세요. FC서울입니다")
    @Size(min = 0, max = 255, message = "구단 소개글은 2자 이상 255자 이하로 입력해야 합니다.")
    private String clubIntroduction;

    @Schema(description = "구단 코드", example = "FCSeoul")
    @NotNull(message = "구단 코드를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "구단 코드는 영문과 숫자로만 구성되어야 합니다.")
    private String clubCode;

    @Schema(description = "등록 날짜")
    @NotNull(message = "구단 등록 날짜를 입력해주세요.")
    private LocalDateTime erollDate;

    @Schema(description = "구단원 수")
    @NotNull(message = "구단원 수를 입력해주세요.")
    private int memberCount;

    @Schema(description = "자주 운동하는 요일", example = "[\"월\", \"화\"]")
    @NotNull(message = "자주 운동하는 요일을 입력해주세요.")
    private List<PeakDays> days;  // Enum 사용

    @Schema(description = "자주 운동하는 시간대", example = "[\"아침\", \"저녁\"]")
    @NotNull(message = "자주 운동하는 시간대를 입력해주세요.")
    private List<PeakHours> times;  // Enum 사용

    @Schema(description = "실력 등급", example = "입문자")
    @NotNull(message = "실력 등급을 입력해주세요.")
    private String skillLevel;

    @Schema(description = "주 활동 구장", example = "서울월드컵경기장")
    @NotNull(message = "주로 사용하는 경기장을 입력해주세요.")
    private String stadiumName;

    @Schema(description = "활동 도시", example = "서울")
    @NotNull(message = "활동하는 도시를 입력해주세요.")
    private String city;

    @Schema(description = "활동 지역", example = "마포구")
    @NotNull(message = "활동하는 지역을 입력해주세요.")
    private String region;

    @Schema(description = "연령대", example = "20대")
    @NotNull(message = "연령대를 입력해주세요.")
    private String ageGroup;

    @Schema(description = "성별", example = "남성")
    @NotNull(message = "성별을 입력해주세요.")
    private String gender;

    public ClubRegistRequestDTO() {
    }

    public ClubRegistRequestDTO(Long userId, String clubName, String clubIntroduction, String clubCode,
                                LocalDateTime erollDate, int memberCount, List<PeakDays> days, List<PeakHours> times, String skillLevel,
                                String stadiumName, String city, String region, String ageGroup, String gender) {
        this.userId = userId;
        this.clubName = clubName;
        this.clubIntroduction = clubIntroduction;
        this.clubCode = clubCode;
        this.erollDate = erollDate;
        this.memberCount = memberCount;
        this.days = days;
        this.times = times;
        this.skillLevel = skillLevel;
        this.stadiumName = stadiumName;
        this.city = city;
        this.region = region;
        this.ageGroup = ageGroup;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "ClubRegistRequestDTO{" +
                ", clubOwner=" + userId +
                ", clubName='" + clubName + '\'' +
                ", clubIntroduction='" + clubIntroduction + '\'' +
                ", clubCode='" + clubCode + '\'' +
                ", erollDate=" + erollDate +
                ", memberCount=" + memberCount +
                ", peakHours=" + times +
                ", peakDays=" + days +
                ", skillLevel='" + skillLevel + '\'' +
                ", stadiumName='" + stadiumName + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", ageGroup='" + ageGroup + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
