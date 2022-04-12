package ablyAssignment.memberManager.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
    MAIL_SEND_ERROR(450,"EMAIL-ERR-450","EMAIL SENDING ERROR"),
    NOT_SMS_VERIFIED_USER(401,"MEMBER-ERR-401","NOT SMS VERIFIED USER ERROR"),
    MEMBER_NOT_FOUND(404,"MEMBER-ERR-404","MEMBER NOT FOUND ERROR"),
    ERROR_PASSWORD(401,"WRONG-PW-ERR-401","WRONG PASSWORD ERROR"),
    DUPLICATED_MEMBER(400,"DUPLICATED-ERR","DUPLICATED MEMBER ERROR"),
    INVALID_TOKEN(401,"INVALID-TOKEN-ERR-401","TOKEN NOT FOUND ERROR");

    private int status;
    private String errorCode;
    private String message;
}
