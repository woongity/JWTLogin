package ablyAssignment.memberManager.exception;

import lombok.Getter;

@Getter
public class TokenNotFoundException extends RuntimeException{
    private ErrorCode errorCode;
    public TokenNotFoundException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
