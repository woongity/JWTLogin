package ablyAssignment.memberManager;

import ablyAssignment.memberManager.domain.Member;
import ablyAssignment.memberManager.dto.SignUpDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemberManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberManagerApplication.class, args);
	}
}