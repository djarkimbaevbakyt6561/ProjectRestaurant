package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "menu_items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_item_seq")
    @SequenceGenerator(name = "menu_item_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    @Column(name = "is_vegetarian")
    private boolean isVegetarian;

    @ManyToMany
    private List<Cheque> cheques;

    @ManyToOne
    private Restaurant restaurant;

    @OneToOne(mappedBy = "menuItem", cascade = CascadeType.REMOVE)
    private StopList stopList;

    @OneToMany
    private List<Subcategory> subcategories;
}
