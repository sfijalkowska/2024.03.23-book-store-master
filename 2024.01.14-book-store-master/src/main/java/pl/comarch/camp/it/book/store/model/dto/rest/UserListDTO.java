package pl.comarch.camp.it.book.store.model.dto.rest;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserListDTO {
    private List<UserDTO> users = new ArrayList<>();
}
