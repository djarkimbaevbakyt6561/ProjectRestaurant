package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @SequenceGenerator(name = "restaurant_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String location;
    @Column(name = "rest_type")
    private String restType;
    @Column(name = "number_of_employees")
    private Byte numberOfEmployees;
    private String service;

    @OneToMany(mappedBy = "restaurant")
    private List<User> users;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    private List<MenuItem> menuItems;

}
