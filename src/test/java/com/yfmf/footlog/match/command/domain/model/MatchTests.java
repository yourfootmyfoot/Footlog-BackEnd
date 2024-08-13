package com.yfmf.footlog.match.command.domain.model;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest
public class MatchTests {

    @Autowired
    private MatchService matchService;


    @DisplayName("경기 전체 목록 조회 테스트")
    @ParameterizedTest
    @Test
    private void testLoadAllMatch() {

        Assertions.assertDoesNotThrow(
                () -> {
                    List<LoadMatchResponseDTO> matches = matchService.loadMatchInfo();
                    matches.forEach(System.out::println);
                }
        );
    }


}
