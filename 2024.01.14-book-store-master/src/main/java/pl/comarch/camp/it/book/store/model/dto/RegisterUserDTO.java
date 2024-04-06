package pl.comarch.camp.it.book.store.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserDTO {
    private String name;
    private String surname;
    private String login;
    private String password;
    private String password2;
}
