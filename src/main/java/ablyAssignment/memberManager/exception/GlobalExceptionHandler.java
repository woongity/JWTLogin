package ablyAssignment.memberManager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotSmsVerifiedException.class)
    public ResponseEntity<ErrorResponse> handleNotSmsVerifiedException(NotSmsVerifiedException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.NOT_SMS_VERIFIED_USER);
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundMemberException(NotFoundMemberException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.MEMBER_NOT_FOUND);
        return new ResponseEntity<>(response,HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundTokenException(TokenNotFoundException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_TOKEN);
        return new ResponseEntity<>(response,HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }
    
    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponse> handleWrongPasswordException(WrongPasswordException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.ERROR_PASSWORD);
        return new ResponseEntity<>(response,HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(DuplicatedMemberException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedMemberException(DuplicatedMemberException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.DUPLICATED_MEMBER);
        return new ResponseEntity<>(response,HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }
}