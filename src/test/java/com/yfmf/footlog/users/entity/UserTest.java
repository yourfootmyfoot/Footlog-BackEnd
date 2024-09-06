package com.yfmf.footlog.users.entity;

import com.yfmf.footlog.enums.Area;
import com.yfmf.footlog.enums.MainFoot;
import com.yfmf.footlog.enums.Position;
import com.yfmf.footlog.users.UserRole;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class UserTest {

//    private static Stream<Arguments> getUser() {
//        return Stream.of(
//                Arguments.of(
//                        1L,
//                        1L,
//                        "테스트 유저 1",
//                        LocalDate.now(),
//                        MainFoot.왼발,
//                        Area.서울,
//                        Position.ST,
//                        "자기 소개",
//                        false,
//                        172.2,
//                        73.4,
//                        "URL이 어쩌구 저쩌구",
//                        "010-1234-5678",
//                        UserRole.ROLE_USER,
//                        new Stat(
//                                20,
//                                40,
//                                50,
//                                60,
//                                80,
//                                100
//                        ),
//                        new Record(
//                                1,
//                                2,
//                                3,
//                                4
//                        )
//                )
//        );
//    }

//    @ParameterizedTest
//    @MethodSource("getUser")
//    void createUserTest(Long userId, Long socialId, String userName, LocalDate birth, MainFoot mainFoot, Area area,
//                        Position position, String introduction, Boolean isPro, Double height, Double weight,
//                        String profileImageUrl, String phoneNumber, UserRole role, Stat stat, Record record) {
//
//        User user = new User(
//                userId, socialId, userName, birth, mainFoot, area, position, introduction, isPro,
//                height, weight, profileImageUrl, phoneNumber, role, stat, record
//        );
//
//        assertThat(user.getUserId()).isEqualTo(userId);
//        assertThat(user.getSocialId()).isEqualTo(socialId);
//        assertThat(user.getUserName()).isEqualTo(userName);
//        assertThat(user.getBirth()).isEqualTo(birth);
//        assertThat(user.getMainFoot()).isEqualTo(mainFoot);
//        assertThat(user.getArea()).isEqualTo(area);
//        assertThat(user.getPosition()).isEqualTo(position);
//        assertThat(user.getIntroduction()).isEqualTo(introduction);
//        assertThat(user.getIsPro()).isEqualTo(isPro);
//        assertThat(user.getHeight()).isEqualTo(height);
//        assertThat(user.getWeight()).isEqualTo(weight);
//        assertThat(user.getProfileImageUrl()).isEqualTo(profileImageUrl);
//        assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
//        assertThat(user.getRole()).isEqualTo(role);
//        assertThat(user.getStat()).isEqualTo(stat);
//        assertThat(user.getRecord()).isEqualTo(record);
//    }

}