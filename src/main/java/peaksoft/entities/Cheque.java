package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cheques")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheque_seq")
    @SequenceGenerator(name = "cheque_seq", allocationSize = 1)
    private Long id;
    @Column(name = "price_average")
    private BigDecimal priceAverage;
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = ZonedDateTime.now();
    }

    @ManyToOne
    private User user;

    @ManyToMany(mappedBy = "cheques")
    private List<MenuItem> menuItems;
}
