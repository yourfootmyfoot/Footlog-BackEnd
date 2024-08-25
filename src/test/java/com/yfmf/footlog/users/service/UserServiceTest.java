package com.yfmf.footlog.users.service;

import com.yfmf.footlog.enums.Area;
import com.yfmf.footlog.enums.MainFoot;
import com.yfmf.footlog.enums.Position;
import com.yfmf.footlog.users.UserRole;
import com.yfmf.footlog.users.dto.UserSaveRequestDto;
import com.yfmf.footlog.users.dto.UserUpdateRequestDto;
import com.yfmf.footlog.users.entity.Record;
import com.yfmf.footlog.users.entity.Stat;
import com.yfmf.footlog.users.entity.User;
import com.yfmf.footlog.users.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {

        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .kakaoId(1L)
                .userName("userName")
                .birth(LocalDate.now())
                .mainFoot(MainFoot.양발)
                .area(Area.경기)
                .position(Position.CB)
                .introduction("introduction")
                .isPro(true)
                .height(172.1)
                .weight(77.7)
                .profileImageUrl("profileImageUrl")
                .phoneNumber("phoneNumber")
                .role(UserRole.ROLE_ADMIN)
                .stat(new Stat(
                        20,
                        40,
                        50,
                        60,
                        80,
                        100
                ))
                .record(new Record(
                        1,
                        2,
                        3,
                        4
                ))
                .build();

        userService.save(requestDto);
    }

    private static Stream<Arguments> getUser() {
        return Stream.of(
                Arguments.of(
                        2L,
                        "테스트 유저 1",
                        LocalDate.now(),
                        MainFoot.왼발,
                        Area.서울,
                        Position.ST,
                        "자기 소개",
                        false,
                        172.2,
                        73.4,
                        "URL이 어쩌구 저쩌구",
                        "010-1234-5678",
                        UserRole.ROLE_USER,
                        new Stat(
                                20,
                                40,
                                50,
                                60,
                                80,
                                100
                        ),
                        new Record(
                                1,
                                2,
                                3,
                                4
                        )
                )
        );
    }

    @DisplayName("User 생성 테스트")
    @ParameterizedTest
    @MethodSource("getUser")
    void saveUserTest(Long kakaoId, String userName, LocalDate birth, MainFoot mainFoot, Area area,
                      Position position, String introduction, Boolean isPro, Double height, Double weight,
                      String profileImageUrl, String phoneNumber, UserRole role, Stat stat, Record record) {

        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .kakaoId(kakaoId)
                .userName(userName)
                .birth(birth)
                .mainFoot(mainFoot)
                .area(area)
                .position(position)
                .introduction(introduction)
                .isPro(isPro)
                .height(height)
                .weight(weight)
                .profileImageUrl(profileImageUrl)
                .phoneNumber(phoneNumber)
                .role(role)
                .stat(stat)
                .record(record)
                .build();

        userService.save(requestDto);

        List<User> userList = userService.findAll();

        assertThat(userList.size()).isEqualTo(2);
    }

    @DisplayName("유저 수정 테스트")
    @ParameterizedTest
    @MethodSource("getUser")
    void userDeleteTest(Long kakaoId, String userName, LocalDate birth, MainFoot mainFoot, Area area,
                        Position position, String introduction, Boolean isPro, Double height, Double weight,
                        String profileImageUrl, String phoneNumber, UserRole role, Stat stat, Record record) {

        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .kakaoId(kakaoId)
                .userName(userName)
                .birth(birth)
                .mainFoot(mainFoot)
                .area(area)
                .position(position)
                .introduction(introduction)
                .isPro(isPro)
                .height(height)
                .weight(weight)
                .profileImageUrl(profileImageUrl)
                .phoneNumber(phoneNumber)
                .role(role)
                .stat(stat)
                .record(record)
                .build();

        Long updateId = userService.save(userSaveRequestDto);

        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(
                "변경된 테스트 유저 1",
                LocalDate.now(),
                MainFoot.왼발,
                Area.서울,
                Position.ST,
                "자기 소개",
                false,
                172.2,
                73.4,
                "URL이 어쩌구 저쩌구",
                "010-1234-5678",
                UserRole.ROLE_USER,
                new Stat(
                        20,
                        40,
                        50,
                        60,
                        80,
                        100
                ),
                new Record(
                        1,
                        2,
                        3,
                        4
                )
        );

        userService.update(updateId, userUpdateRequestDto);

        User updatedUser = userService.findById(updateId);

        assertThat(updatedUser.getUserName()).isEqualTo("변경된 테스트 유저 1");
    }
}