package ablyAssignment.memberManager.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class NotSmsVerifiedException extends RuntimeException{
    private ErrorCode errorCode;
    public NotSmsVerifiedException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
