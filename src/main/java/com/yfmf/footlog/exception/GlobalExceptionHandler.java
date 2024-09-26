package com.yfmf.footlog.exception;

import com.yfmf.footlog.domain.auth.exception.LoginRequiredException;
import com.yfmf.footlog.domain.club.exception.ClubDuplicatedException;
import com.yfmf.footlog.domain.club.exception.ClubNotFoundException;
import com.yfmf.footlog.domain.club.exception.IllegalClubArgumentException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice  // 모든 컨트롤러에서 발생하는 예외를 처리
public class GlobalExceptionHandler {

    // 공통적으로 ErrorResponse를 생성하는 메서드
    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String errorType, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(status.value());
        errorResponse.setErrorType(errorType);
        errorResponse.setMessage(message);
        return new ResponseEntity<>(errorResponse, status);
    }


    /****
     * 클럽관련예외
     ****/

    // 일반적인 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>("서버에서 문제가 발생했습니다. 나중에 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 유효성 검사 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("유효성 검사 실패: {}", ex.getMessage());

        StringBuilder message = new StringBuilder("유효성 검사 오류: ");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                message.append(String.format("[%s: %s] ", error.getField(), error.getDefaultMessage()))
        );

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", message.toString());
    }


    // 잘못된 타입의 값이 전달될 때 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("잘못된 타입의 값이 전달되었습니다: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Type Mismatch", "잘못된 값이 입력되었습니다. 요청한 값의 타입이 맞지 않습니다.");
    }


    // 빈 요청 본문이 들어왔을 때 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("읽을 수 없는 요청 본문: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Unreadable Message", "요청 본문이 잘못되었습니다. 올바른 형식으로 요청해주세요.");
    }

    /****
     * 클럽관련예외
     ****/
    // 클럽 중복 예외 처리
    @ExceptionHandler(ClubDuplicatedException.class)
    public ResponseEntity<ErrorResponse> handleClubDuplicatedException(ClubDuplicatedException ex) {
        log.error("ClubDuplicatedException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, "Duplicated Club", ex.getMessage());
    }

    // 클럽을 찾지 못했을 때 처리하는 예외 핸들러
    @ExceptionHandler(ClubNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClubNotFoundException(ClubNotFoundException ex) {
        log.error("ClubNotFoundException: {}", ex.getMessage());  // 예외 메시지 로깅
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found Club", ex.getMessage());
    }


    // 클럽 인자값 예외 처리
    @ExceptionHandler(IllegalClubArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalClubArgumentException(IllegalClubArgumentException ex) {
        log.error("IllegalClubArgumentException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getErrorCode().getErrorType(), ex.getMessage());
    }


    /****
     * 인증 및 접근 권한 관련 예외 처리
     ****/

    // 로그인 필요 예외 처리
    @ExceptionHandler(LoginRequiredException.class)
    public ResponseEntity<ErrorResponse> handleLoginRequiredException(LoginRequiredException ex) {
        log.error("LoginRequiredException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getErrorCode().getErrorType(), ex.getMessage());
    }

    // 접근 권한이 없는 경우 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("AccessDeniedException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.FORBIDDEN, "Access Denied", "접근 권한이 없습니다.");
    }

    /****
     * 데이터베이스 관련 예외 처리
     ****/

    // 엔터티를 찾지 못했을 때 처리
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("EntityNotFoundException: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Entity Not Found", "요청한 리소스를 찾을 수 없습니다.");
    }


}
