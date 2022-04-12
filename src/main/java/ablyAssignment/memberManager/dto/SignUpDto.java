package ablyAssignment.memberManager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor @AllArgsConstructor
@Getter
public class SignUpDto {
     @NotBlank
     String name;

     @NotBlank
     String nickname;

     @NotBlank
     String password;

     @NotBlank
     String phoneNo;

     @NotBlank @Email
     String email;
}
