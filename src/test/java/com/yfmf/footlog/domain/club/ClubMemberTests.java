package com.yfmf.footlog.domain.club;

import com.yfmf.footlog.domain.club.entity.ClubMember;
import com.yfmf.footlog.domain.club.exception.ClubNotFoundException;
import com.yfmf.footlog.domain.club.repository.ClubMemberRepository;
import com.yfmf.footlog.domain.club.repository.ClubRepository;
import com.yfmf.footlog.domain.club.service.ClubMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class ClubMemberTests {
    @Mock
    private ClubRepository clubRepository;

    @Mock
    private ClubMemberRepository clubMemberRepository;

    @InjectMocks
    private ClubMemberService clubMemberService;

    public ClubMemberTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("사용자가 구단에 성공적으로 가입한다")
    void joinClub_success() {
        // given
        Long userId = 1L;
        Long clubId = 1L;

        given(clubRepository.existsById(clubId)).willReturn(true);
        given(clubMemberRepository.existsByMemberIdAndClubId(userId, clubId)).willReturn(false);

        // when
        clubMemberService.joinClub(userId, clubId);

        // then
        then(clubRepository).should().existsById(clubId);
        then(clubMemberRepository).should().existsByMemberIdAndClubId(userId, clubId);
        then(clubMemberRepository).should().save(any(ClubMember.class));
    }

    @Test
    @DisplayName("존재하지 않는 구단에 가입하려고 할 때 예외가 발생한다")
    void joinClub_clubNotFound() {
        // given
        Long userId = 1L;
        Long clubId = 999L;

        given(clubRepository.existsById(clubId)).willReturn(false);

        // when // then
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> clubMemberService.joinClub(userId, clubId))
                .isInstanceOf(ClubNotFoundException.class)
                .hasMessageContaining("구단을 찾을 수 없습니다.");

        then(clubRepository).should().existsById(clubId);
        then(clubMemberRepository).should(never()).existsByMemberIdAndClubId(anyLong(), anyLong());
        then(clubMemberRepository).should(never()).save(any(ClubMember.class));
    }

    @Test
    @DisplayName("이미 가입된 구단에 다시 가입하려고 할 때 예외가 발생한다")
    void joinClub_alreadyMember() {
        // given
        Long userId = 1L;
        Long clubId = 1L;

        given(clubRepository.existsById(clubId)).willReturn(true);
        given(clubMemberRepository.existsByMemberIdAndClubId(userId, clubId)).willReturn(true);

        // when // then
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> clubMemberService.joinClub(userId, clubId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 구단에 가입된 회원입니다.");

        then(clubRepository).should().existsById(clubId);
        then(clubMemberRepository).should().existsByMemberIdAndClubId(userId, clubId);
        then(clubMemberRepository).should(never()).save(any(ClubMember.class));
    }

    @Test
    @DisplayName("구단에서 성공적으로 탈퇴한다")
    void leaveClub_success() {
        // given
        Long userId = 1L;
        Long clubId = 1L;

        given(clubRepository.existsById(clubId)).willReturn(true);
        given(clubMemberRepository.existsByMemberIdAndClubId(userId, clubId)).willReturn(true);

        // when
        clubMemberService.leaveClub(userId, clubId);

        // then
        then(clubRepository).should().existsById(clubId);
        then(clubMemberRepository).should().existsByMemberIdAndClubId(userId, clubId);
        then(clubMemberRepository).should().deleteByMemberIdAndClubId(userId, clubId);
    }

    @Test
    @DisplayName("존재하지 않는 구단에서 탈퇴하려고 할 때 예외가 발생한다")
    void leaveClub_clubNotFound() {
        // given
        Long userId = 1L;
        Long clubId = 999L;

        given(clubRepository.existsById(clubId)).willReturn(false);

        // when // then
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> clubMemberService.leaveClub(userId, clubId))
                .isInstanceOf(ClubNotFoundException.class)
                .hasMessageContaining("구단을 찾을 수 없습니다.");

        then(clubRepository).should().existsById(clubId);
        then(clubMemberRepository).should(never()).existsByMemberIdAndClubId(anyLong(), anyLong());
        then(clubMemberRepository).should(never()).deleteByMemberIdAndClubId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("가입되지 않은 구단에서 탈퇴하려고 할 때 예외가 발생한다")
    void leaveClub_notMember() {
        // given
        Long userId = 1L;
        Long clubId = 1L;

        given(clubRepository.existsById(clubId)).willReturn(true);
        given(clubMemberRepository.existsByMemberIdAndClubId(userId, clubId)).willReturn(false);

        // when // then
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> clubMemberService.leaveClub(userId, clubId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 회원은 구단에 가입되어 있지 않습니다.");

        then(clubRepository).should().existsById(clubId);
        then(clubMemberRepository).should().existsByMemberIdAndClubId(userId, clubId);
        then(clubMemberRepository).should(never()).deleteByMemberIdAndClubId(userId, clubId);
    }
}


