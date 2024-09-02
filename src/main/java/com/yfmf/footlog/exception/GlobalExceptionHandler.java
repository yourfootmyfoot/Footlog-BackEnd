package com.yfmf.footlog.exception;

import com.yfmf.footlog.domain.auth.exception.LoginRequiredException;
import com.yfmf.footlog.domain.club.exception.ClubDuplicatedException;
import com.yfmf.footlog.domain.club.exception.ClubNotFoundException;
import com.yfmf.footlog.domain.club.exception.IllegalClubArgumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 일반적인 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>("서버에서 문제가 발생했습니다. 나중에 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 검증 오류 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>("요청이 잘못되었습니다. 입력 데이터를 확인하세요.", HttpStatus.BAD_REQUEST);
    }

    // 구단 관련 오류 처리
    @ExceptionHandler(ClubDuplicatedException.class)
    public ResponseEntity<ErrorResponse> handleClubDuplicatedException(ClubDuplicatedException e) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorType(e.getErrorCode().getErrorType());
        response.setMessage(e.getMessage());
        response.setStatus(e.getErrorCode().getStatus().value());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ClubNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClubNotFoundException(ClubNotFoundException e) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorType(e.getErrorCode().getErrorType());
        response.setMessage(e.getMessage());
        response.setStatus(e.getErrorCode().getStatus().value());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(IllegalClubArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalClubArgumentException(IllegalClubArgumentException e) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorType(e.getErrorCode().getErrorType());
        response.setMessage(e.getMessage());
        response.setStatus(e.getErrorCode().getStatus().value());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Auth 관련 오류 처리
    @ExceptionHandler(LoginRequiredException.class)
    public ResponseEntity<ErrorResponse> handleLoginRequiredException(LoginRequiredException e) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorType(e.getErrorCode().getErrorType());
        response.setMessage(e.getMessage());
        response.setStatus(e.getErrorCode().getStatus().value());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
