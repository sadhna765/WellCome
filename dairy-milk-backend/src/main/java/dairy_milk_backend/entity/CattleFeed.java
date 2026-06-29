package dairy_milk_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cattle_feed")
public class CattleFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    private LocalDate feedDate;

    private String feedType; // "Mineral Mixture"

    private Double quantity;

    private Double rate;

    private Double amount;
}