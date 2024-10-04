package com.yfmf.footlog.domain.club;

import com.yfmf.footlog.domain.auth.dto.LoginedInfo;
import com.yfmf.footlog.domain.auth.exception.LoginRequiredException;
import com.yfmf.footlog.domain.club.controller.ClubController;
import com.yfmf.footlog.domain.club.dto.ClubRegistRequestDTO;
import com.yfmf.footlog.domain.club.dto.ClubRegistResponseDTO;
import com.yfmf.footlog.domain.club.entity.Club;
import com.yfmf.footlog.domain.club.exception.ClubNotFoundException;
import com.yfmf.footlog.domain.club.service.ClubService;
import com.yfmf.footlog.domain.member.domain.Authority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ClubControllerTest {

    // 테스트할 ClubController 클래스의 인스턴스를 생성하고 의존성 주입
    @InjectMocks
    private ClubController clubController;

    // Mock으로 ClubService를 선언하여 실제 인스턴스 대신 Mock 동작을 정의할 수 있음
    @Mock
    private ClubService clubService;

    // 각 테스트 메서드 실행 전에 Mockito로 선언된 Mock 객체들을 초기화
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("구단 등록 성공")
    void createClub_Success() {
        // given: 테스트에 필요한 입력 데이터와 예상되는 동작 설정
        ClubRegistRequestDTO requestDTO = new ClubRegistRequestDTO();
        LoginedInfo loginedInfo = new LoginedInfo(1L, "testuser", "test@example.com", Authority.USER); // 로그인된 사용자 정보
        ClubRegistResponseDTO responseDTO = new ClubRegistResponseDTO();

        // 구단 등록 시 service가 성공적으로 처리한다고 가정
        when(clubService.registClub(any(ClubRegistRequestDTO.class))).thenReturn(responseDTO);

        // when: 클럽 등록 API 호출
        ResponseEntity<ClubRegistResponseDTO> response = clubController.createClub(requestDTO, loginedInfo);

        // then: 기대한 결과와 비교하여 검증
        assertEquals(HttpStatus.CREATED, response.getStatusCode());  // 응답 상태가 201(CREATED)인지 확인
        assertNotNull(response.getBody());  // 응답이 null이 아닌지 확인
        verify(clubService, times(1)).registClub(any(ClubRegistRequestDTO.class));  // registClub 메서드가 한 번 호출되었는지 확인
    }

    @Test
    @DisplayName("로그인하지 않은 사용자가 구단 등록 시도 시 예외 발생")
    void createClub_Unauthorized() {
        // given: 구단 등록 요청 생성
        ClubRegistRequestDTO requestDTO = new ClubRegistRequestDTO();

        // when & then: 로그인되지 않은 상태에서 요청하면 LoginRequiredException 발생 확인
        assertThrows(LoginRequiredException.class, () -> clubController.createClub(requestDTO, null));
        verify(clubService, never()).registClub(any(ClubRegistRequestDTO.class));  // service 메서드가 호출되지 않음을 확인
    }

    @Test
    @DisplayName("모든 구단 조회 성공")
    void getAllClubs_Success() {
        // given: 조회할 클럽 리스트 설정
        List<Club> clubs = List.of(new Club(), new Club());
        when(clubService.getAllClubs()).thenReturn(clubs);

        // when: 클럽 목록 조회 API 호출
        List<Club> result = clubController.getAllClubs();

        // then: 클럽 리스트가 정상적으로 반환되는지 확인
        assertNotNull(result);  // 결과가 null이 아님을 확인
        assertEquals(2, result.size());  // 조회된 클럽 수가 예상한 만큼인지 확인
        verify(clubService, times(1)).getAllClubs();  // getAllClubs 메서드가 한 번 호출되었는지 확인
    }

    @Test
    @DisplayName("구단주 ID로 구단 조회 성공")
    void getClubsByUserId_Success() {
        // given: 특정 구단주가 소유한 클럽 리스트 설정
        Long userId = 1L;
        List<Club> clubs = List.of(new Club());
        when(clubService.getClubsByUserId(userId)).thenReturn(clubs);

        // when: 구단주 ID로 클럽 조회 API 호출
        ResponseEntity<List<Club>> response = clubController.getClubsByUserId(userId);

        // then: 조회된 클럽 리스트 확인
        assertEquals(HttpStatus.OK, response.getStatusCode());  // 응답 상태가 200(OK)인지 확인
        assertNotNull(response.getBody());  // 응답 내용이 null이 아님을 확인
        verify(clubService, times(1)).getClubsByUserId(userId);  // getClubsByUserId 메서드가 한 번 호출되었는지 확인
    }

    @Test
    @DisplayName("구단주 ID로 구단 조회 시 구단이 없을 때 예외 발생")
    void getClubsByUserId_NotFound() {
        // given: 구단주 ID로 조회 시 클럽이 없음을 설정
        Long userId = 1L;
        when(clubService.getClubsByUserId(userId)).thenThrow(new ClubNotFoundException("구단이 존재하지 않습니다.", "[ClubService] getClubsByUserId"));

        // when: 구단주 ID로 클럽 조회 API 호출
        ResponseEntity<List<Club>> response = clubController.getClubsByUserId(userId);

        // then: 클럽이 없을 때 404 응답 확인
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());  // 응답 상태가 404(NOT_FOUND)인지 확인
        verify(clubService, times(1)).getClubsByUserId(userId);  // getClubsByUserId 메서드가 호출되었는지 확인
    }

    @Test
    @DisplayName("구단 ID로 구단 조회 성공")
    void getClubById_Success() {
        // given: 특정 구단 ID로 조회할 클럽 설정
        Long clubId = 1L;
        Club club = new Club();
        LoginedInfo loginedInfo = new LoginedInfo(1L, "testuser", "test@example.com", Authority.USER);  // 로그인 정보 포함
        when(clubService.getClubByClubId(clubId)).thenReturn(club);

        // when: 구단 ID로 클럽 조회 API 호출
        ResponseEntity<Club> response = clubController.getClubById(clubId, loginedInfo);

        // then: 조회된 클럽이 정상적으로 반환되는지 확인
        assertEquals(HttpStatus.OK, response.getStatusCode());  // 응답 상태가 200(OK)인지 확인
        assertNotNull(response.getBody());  // 응답 내용이 null이 아님을 확인
        verify(clubService, times(1)).getClubByClubId(clubId);  // getClubByClubId 메서드가 한 번 호출되었는지 확인
    }

    @Test
    @DisplayName("로그인하지 않은 사용자가 구단 조회 시 예외 발생")
    void getClubById_Unauthorized() {
        // given: 구단 ID로 조회
        Long clubId = 1L;

        // when & then: 로그인하지 않은 상태에서 요청 시 LoginRequiredException 발생 확인
        assertThrows(LoginRequiredException.class, () -> clubController.getClubById(clubId, null));
        verify(clubService, never()).getClubByClubId(anyLong());  // getClubByClubId 메서드가 호출되지 않았음을 확인
    }

    @Test
    @DisplayName("구단 ID로 구단 조회 시 구단이 없을 때 예외 발생")
    void getClubById_NotFound() {
        // given: 구단 ID로 조회 시 클럽이 없음을 설정
        Long clubId = 1L;
        LoginedInfo loginedInfo = new LoginedInfo(1L, "testuser", "test@example.com", Authority.USER);  // 로그인 정보 포함
        when(clubService.getClubByClubId(clubId)).thenThrow(new ClubNotFoundException("구단이 존재하지 않습니다.", "[ClubService] getClubById"));

        // when: 구단 ID로 클럽 조회 API 호출
        ResponseEntity<Club> response = clubController.getClubById(clubId, loginedInfo);

        // then: 클럽이 없을 때 404 응답 확인
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());  // 응답 상태가 404(NOT_FOUND)인지 확인
        verify(clubService, times(1)).getClubByClubId(clubId);  // getClubByClubId 메서드가 호출되었는지 확인
    }

    @Test
    @DisplayName("구단 업데이트 성공")
    void updateClub_Success() {
        // given: 구단 업데이트 요청 설정
        Long clubId = 1L;
        ClubRegistRequestDTO requestDTO = new ClubRegistRequestDTO();
        LoginedInfo loginedInfo = new LoginedInfo(1L, "testuser", "test@example.com", Authority.USER);  // 로그인 정보 포함

        // when: 구단 업데이트 API 호출
        ResponseEntity<String> response = clubController.updateClub(clubId, requestDTO, loginedInfo);

        // then: 업데이트가 성공적으로 이루어졌는지 확인
        assertEquals(HttpStatus.OK, response.getStatusCode());  // 응답 상태가 200(OK)인지 확인
        assertEquals("구단이 성공적으로 업데이트되었습니다.", response.getBody());  // 응답 메시지가 예상과 일치하는지 확인
        verify(clubService, times(1)).updateClub(clubId, requestDTO);  // updateClub 메서드가 호출되었는지 확인
    }

    @Test
    @DisplayName("로그인하지 않은 사용자가 구단 업데이트 시 예외 발생")
    void updateClub_Unauthorized() {
        // given: 구단 업데이트 요청
        Long clubId = 1L;
        ClubRegistRequestDTO requestDTO = new ClubRegistRequestDTO();

        // when & then: 로그인하지 않은 상태에서 요청 시 예외 발생 확인
        assertThrows(LoginRequiredException.class, () -> clubController.updateClub(clubId, requestDTO, null));
        verify(clubService, never()).updateClub(anyLong(), any(ClubRegistRequestDTO.class));  // updateClub 메서드가 호출되지 않았음을 확인
    }

    @Test
    @DisplayName("구단 업데이트 시 구단이 없을 때 예외 발생")
    void updateClub_NotFound() {
        // given: 구단 ID로 업데이트할 클럽이 없음을 설정
        Long clubId = 1L;
        ClubRegistRequestDTO requestDTO = new ClubRegistRequestDTO();
        LoginedInfo loginedInfo = new LoginedInfo(1L, "testuser", "test@example.com", Authority.USER);  // 로그인 정보 포함
        doThrow(new ClubNotFoundException("업데이트할 구단이 존재하지 않습니다.", "[ClubService] updateClub"))
                .when(clubService).updateClub(clubId, requestDTO);

        // when: 구단 업데이트 API 호출
        ResponseEntity<String> response = clubController.updateClub(clubId, requestDTO, loginedInfo);

        // then: 클럽이 없을 때 404 응답 확인
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());  // 응답 상태가 404(NOT_FOUND)인지 확인
        verify(clubService, times(1)).updateClub(clubId, requestDTO);  // updateClub 메서드가 호출되었는지 확인
    }

    @Test
    @DisplayName("구단 삭제 성공")
    void deleteClub_Success() {
        // given: 삭제할 클럽 ID 설정
        Long clubId = 1L;

        // when: 구단 삭제 API 호출
        ResponseEntity<String> response = clubController.deleteClub(clubId);

        // then: 구단 삭제가 성공적으로 이루어졌는지 확인
        assertEquals(HttpStatus.OK, response.getStatusCode());  // 응답 상태가 200(OK)인지 확인
        assertEquals("구단이 성공적으로 삭제되었습니다.", response.getBody());  // 응답 메시지가 예상과 일치하는지 확인
        verify(clubService, times(1)).deleteClub(clubId);  // deleteClub 메서드가 호출되었는지 확인
    }

    @Test
    @DisplayName("구단 삭제 시 구단이 없을 때 예외 발생")
    void deleteClub_NotFound() {
        // given: 삭제할 클럽이 없음을 설정
        Long clubId = 1L;
        doThrow(new ClubNotFoundException("삭제할 구단이 존재하지 않습니다.", "[ClubService] deleteClub"))
                .when(clubService).deleteClub(clubId);

        // when: 구단 삭제 API 호출
        ResponseEntity<String> response = clubController.deleteClub(clubId);

        // then: 클럽이 없을 때 404 응답 확인
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());  // 응답 상태가 404(NOT_FOUND)인지 확인
        verify(clubService, times(1)).deleteClub(clubId);  // deleteClub 메서드가 호출되었는지 확인
    }
}