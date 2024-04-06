package pl.comarch.camp.it.book.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "tuser")
public class User implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String login;
    @Column(length = 33)
    private String password;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Transient
    private final Set<Position> cart = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Order> orders = new HashSet<>();

    public User(int id, String login, String password, String name, String surname, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public User(int id) {
        this.id = id;
    }

    public double total() {
        double sum = this.cart.stream()
                .mapToDouble(p -> p.getBook().getPrice().doubleValue() * p.getQuantity())
                .sum();
        return ((int) (sum * 100))/100.0;
    }

    @Override
    public User clone() {
        User user = new User();
        user.setId(this.id);
        user.setLogin(this.login);
        user.setPassword(this.password);
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setRole(this.role);
        user.getCart().addAll(this.getCart().stream().map(Position::clone).toList());

        return user;
    }

    public enum Role {
        USER,
        ADMIN
    }
}
