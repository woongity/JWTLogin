package ablyAssignment.memberManager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class LoginByEmailDto{
    @NotBlank @NotNull
    private String email;
    @NotBlank @NotNull
    private String password;
}
