package pl.comarch.camp.it.book.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "torder")
public class Order implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(precision = 6, scale = 2)
    private BigDecimal total;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<Position> positions = new HashSet<>();

    public Order(int id, LocalDateTime date, Status status, double total, User user) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.total = new BigDecimal(total);
        this.user = user;
    }

    public Order(int id) {
        this.id = id;
    }

    public String getFormattedDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return this.date.format(dateTimeFormatter);
    }

    public double getTotal() {
        return this.total.doubleValue();
    }

    public void setTotal(double total) {
        this.total = new BigDecimal(total);
    }

    public enum Status {
        NEW,
        PAID,
        SENT,
        DONE
    }

    @Override
    public Order clone() {
        Order order = new Order();
        order.setId(this.id);
        //order.setTotal(this.total);
        order.total = this.total;
        order.setStatus(this.status);
        order.setUser(this.user.clone());
        order.getPositions().addAll(this.positions.stream().map(Position::clone).toList());

        return order;
    }
}
