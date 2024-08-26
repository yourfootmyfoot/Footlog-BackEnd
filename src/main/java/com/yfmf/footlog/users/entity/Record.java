package com.yfmf.footlog.users.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class Record {

    private Integer totalMatch;

    private Integer totalScore;

    private Integer totalAssist;

    private Integer totalMom;

    public Record(Integer totalMatch, Integer totalScore, Integer totalAssist, Integer totalMom) {

        validateRecord(totalMatch, totalScore, totalAssist, totalMom);

        this.totalMatch = totalMatch;
        this.totalScore = totalScore;
        this.totalAssist = totalAssist;
        this.totalMom = totalMom;
    }

    private void validateTotalMatch(Integer totalMatch) {

        if (totalMatch < 0) {
            throw new IllegalArgumentException("경기 수는 음수일 수 없습니다.");
        }
    }

    private void validateTotalScore(Integer totalScore) {

        if (totalScore < 0) {
            throw new IllegalArgumentException("총 득점은 음수일 수 없습니다.");
        }
    }

    private void validateTotalAssist(Integer totalAssist) {

        if (totalAssist < 0) {
            throw new IllegalArgumentException("총 어시스트는 음수일 수 없습니다.");
        }
    }

    private void validateTotalMom(Integer totalMom) {

        if (totalMom < 0) {
            throw new IllegalArgumentException("총 Mom은 음수일 수 없습니다.");
        }
    }

    private void validateRecord(Integer totalMatch, Integer totalScore, Integer totalAssist, Integer totalMom) {
        validateTotalMatch(totalMatch);
        validateTotalScore(totalScore);
        validateTotalAssist(totalAssist);
        validateTotalMom(totalMom);
    }
}
