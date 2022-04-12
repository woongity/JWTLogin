package ablyAssignment.memberManager.exception;

import lombok.Getter;

@Getter
public class WrongPasswordException extends RuntimeException{
    private ErrorCode errorCode;
    public WrongPasswordException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
