package ablyAssignment.memberManager.sms;

import ablyAssignment.memberManager.exception.MailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SMSSender {

    public void sendMessage(SMS sms) throws MailException {
        log.info(sms.getTo()+"로 메세지를 보냅니다");
        log.info(sms.getText());
    }
}
