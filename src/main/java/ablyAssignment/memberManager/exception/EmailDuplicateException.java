package ablyAssignment.memberManager.exception;
import lombok.Getter;

@Getter
public class EmailDuplicateException extends RuntimeException{

    private ErrorCode errorCode;
    public EmailDuplicateException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
