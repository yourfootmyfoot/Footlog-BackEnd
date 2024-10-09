package com.yfmf.footlog.error;

import com.yfmf.footlog.domain.auth.exception.LoginRequiredException;
import com.yfmf.footlog.domain.auth.utils.ApiUtils;
import com.yfmf.footlog.domain.club.exception.ClubAlreadyJoinedException;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(ApplicationException e) {
        log.error("Error occurs {}", e.toString());

        Map<String,Object> data = new HashMap<>();
        data.put("status", e.getErrorCode().getStatus().value());
        data.put("errorCode", e.getErrorCode().getCode());
        data.put("message", e.getMessage());
        data.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ApiUtils.error(data));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        log.error("Error occurs {}", e.getMessage());

        ErrorCode error = ErrorCode.INTERNAL_SERVER_ERROR;
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, error.getCode(), "서버에서 문제가 발생했습니다. 나중에 다시 시도해주세요.");
    }

    // 공통적으로 ErrorResponse를 생성하는 메서드
    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String errorType, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(status.value());
        errorResponse.setErrorType(errorType);
        errorResponse.setMessage(message);
        return new ResponseEntity<>(errorResponse, status);
    }

    /**** 유효성 검사 및 타입 처리 예외 ****/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder message = new StringBuilder("유효성 검사 오류: ");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                message.append(String.format("[%s: %s] ", error.getField(), error.getDefaultMessage()))
        );
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", message.toString());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Type Mismatch", "잘못된 값이 입력되었습니다. 요청한 값의 타입이 맞지 않습니다.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Unreadable Message", "요청 본문이 잘못되었습니다. 올바른 형식으로 요청해주세요.");
    }

    /**** 클럽 관련 예외 처리 ****/
    @ExceptionHandler(ClubDuplicatedException.class)
    public ResponseEntity<ErrorResponse> handleClubDuplicatedException(ClubDuplicatedException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Duplicated Club", ex.getMessage());
    }

    @ExceptionHandler(ClubNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClubNotFoundException(ClubNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Not Found Club", ex.getMessage());
    }

    @ExceptionHandler(IllegalClubArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalClubArgumentException(IllegalClubArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getErrorCode().getErrorType(), ex.getMessage());
    }

    @ExceptionHandler(ClubAlreadyJoinedException.class)
    public ResponseEntity<ErrorResponse> handleClubAlreadyJoinedException(ClubAlreadyJoinedException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Club Already Joined", ex.getMessage());
    }

    /**** 인증 및 접근 권한 관련 예외 처리 ****/
    @ExceptionHandler(LoginRequiredException.class)
    public ResponseEntity<ErrorResponse> handleLoginRequiredException(LoginRequiredException ex) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getErrorCode().getErrorType(), ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, "Access Denied", "접근 권한이 없습니다.");
    }

    /**** 데이터베이스 관련 예외 처리 ****/
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Entity Not Found", "요청한 리소스를 찾을 수 없습니다.");
    }
}