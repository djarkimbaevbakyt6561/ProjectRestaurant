package peaksoft.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "stop_lists")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stop_list_seq")
    @SequenceGenerator(name = "stop_list_seq", allocationSize = 1)
    private Long id;
    private String reason;
    private LocalDate date;
    @OneToOne
    private MenuItem menuItem;
}