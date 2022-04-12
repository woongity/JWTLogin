package ablyAssignment.memberManager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class LoginByPhoneNoDto {
    @NotBlank
    String phoneNo;
    @NotBlank
    String password;
}
