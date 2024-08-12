package com.yfmf.footlog.match.command.domain.model;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class MatchTests {

    @Autowired
    private MatchService matchService;


    @DisplayName("경기 생성 테스트")
    @ParameterizedTest
    @MethodSource("newMatch")
    private void testCreateNewMatch(MatchRegistRequestDTO newMatch) {
        Assertions.assertDoesNotThrow(
                () -> matchService.registNewMatch(newMatch)
        );
        matchService.findAllMenus().forEach(System.out::println);
    }


}
