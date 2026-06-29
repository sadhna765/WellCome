package dairy_milk_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "advances")
public class Advance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @Column(nullable = false)
    private LocalDate advanceDate;     // advance ki tarikh

    @Column(nullable = false)
    private Double amount;             // kitna advance liya

    private String reason;             // kyun liya

    @Column(nullable = false)
    private Double balance;            // remaining (recover hona baaki)
}