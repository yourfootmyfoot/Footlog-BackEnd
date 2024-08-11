package com.yfmf.footlog.domain.matchenroll;

import com.yfmf.footlog.domain.matchenroll.enums.ClubLevel;
import com.yfmf.footlog.domain.matchenroll.enums.MatchGender;
import com.yfmf.footlog.domain.matchenroll.enums.PlayerCount;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "MATCH_ENROLL")
public class MatchEnroll {

    @Id
    @GeneratedValue
    private Long matchEnrollId;
    private String matchTitle;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime matchDay;
    private Boolean isPro;
    private Integer matchCost;
    private Integer playTime;
    private Integer quarter;
    private String fieldLocation;

    @Enumerated(EnumType.STRING)
    private PlayerCount playerCount;

    @Enumerated(EnumType.STRING)
    private MatchGender matchGender;

    // user
    private Long userId;

    private String userName;

    //club
    private String clubName;

    private String clubImageUrl;

    private ClubLevel clubLevel;

    protected MatchEnroll() {}

    public MatchEnroll(String matchTitle, String description, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime matchDay, Boolean isPro, Integer matchCost, Integer playTime, Integer quarter, String fieldLocation, PlayerCount playerCount, MatchGender matchGender, Long userId, String userName, String clubName, String clubImageUrl, ClubLevel clubLevel) {
        this.matchTitle = matchTitle;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.matchDay = matchDay;
        this.isPro = isPro;
        this.matchCost = matchCost;
        this.playTime = playTime;
        this.quarter = quarter;
        this.fieldLocation = fieldLocation;
        this.playerCount = playerCount;
        this.matchGender = matchGender;
        this.userId = userId;
        this.userName = userName;
        this.clubName = clubName;
        this.clubImageUrl = clubImageUrl;
        this.clubLevel = clubLevel;
    }

    public Long getMatchEnrollId() {
        return matchEnrollId;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getMatchDay() {
        return matchDay;
    }

    public Boolean getPro() {
        return isPro;
    }

    public Integer getMatchCost() {
        return matchCost;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public String getFieldLocation() {
        return fieldLocation;
    }

    public PlayerCount getPlayerCount() {
        return playerCount;
    }

    public MatchGender getMatchGender() {
        return matchGender;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getClubName() {
        return clubName;
    }

    public String getClubImageUrl() {
        return clubImageUrl;
    }

    public ClubLevel getClubLevel() {
        return clubLevel;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setMatchDay(LocalDateTime matchDay) {
        this.matchDay = matchDay;
    }

    public void setMatchCost(Integer matchCost) {
        this.matchCost = matchCost;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public void setFieldLocation(String fieldLocation) {
        this.fieldLocation = fieldLocation;
    }

    public void setPlayerCount(PlayerCount playerCount) {
        this.playerCount = playerCount;
    }

    public void setMatchGender(MatchGender matchGender) {
        this.matchGender = matchGender;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setClubImageUrl(String clubImageUrl) {
        this.clubImageUrl = clubImageUrl;
    }

    public void setClubLevel(ClubLevel clubLevel) {
        this.clubLevel = clubLevel;
    }

    @Override
    public String toString() {
        return "MatchEnroll{" +
                "matchEnrollId=" + matchEnrollId +
                ", matchTitle='" + matchTitle + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", matchDay=" + matchDay +
                ", isPro=" + isPro +
                ", matchCost=" + matchCost +
                ", playTime=" + playTime +
                ", quarter=" + quarter +
                ", fieldLocation='" + fieldLocation + '\'' +
                ", playerCount=" + playerCount +
                ", matchGender=" + matchGender +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", clubName='" + clubName + '\'' +
                ", clubImageUrl='" + clubImageUrl + '\'' +
                ", clubLevel=" + clubLevel +
                '}';
    }
}
