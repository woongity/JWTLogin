package ablyAssignment.memberManager.exception;

import lombok.Getter;

@Getter
public class DuplicatedMemberException extends RuntimeException{
    private ErrorCode errorCode;

    public DuplicatedMemberException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
