package com.yfmf.footlog.match.command.domain.model;


import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pro {
    // 프로 존재 여부
    @Column(nullable = false)
    private Boolean isPro;

    // 프로 숫자
    @Column
    private Integer proQuantity;

    public Pro(Boolean isPro, Integer proQuantity) {

        this.isPro = isPro;
        this.proQuantity = proQuantity;
    }

    // 선출여부 false 일때 선수 인원 등록 금지
    private void validPro(Boolean isPro, Integer proQuantity) {
        if ((isPro == null || isPro == false) && proQuantity > 0) {
            throw new IllegalArgumentException("서출이 없을때 값을 입력할 수 없습니다.");
        }
    }


}