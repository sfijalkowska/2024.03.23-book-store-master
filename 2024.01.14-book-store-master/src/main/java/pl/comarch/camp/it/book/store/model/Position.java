package pl.comarch.camp.it.book.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "tposition")
public class Position implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Book book;
    private int quantity;

    public void incrementQuantity() {
        this.quantity++;
    }

    @Override
    public Position clone() {
        Position position = new Position();
        position.setId(this.getId());
        position.setBook(this.getBook().clone());
        position.setQuantity(this.quantity);

        return position;
    }
}
