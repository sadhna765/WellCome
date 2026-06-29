package dairy_milk_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "milk_rate")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MilkType milkType;     // COW or BUFFALO

    @Column(nullable = false)
    private Double minFat;         // inclusive lower bound of fat %

    @Column(nullable = false)
    private Double maxFat;         // inclusive upper bound of fat %

    @Column(nullable = false)
    private Double ratePerLitre;

    @Column(nullable = false)
    private Boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    // ---- getters & setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MilkType getMilkType() { return milkType; }
    public void setMilkType(MilkType milkType) { this.milkType = milkType; }

    public Double getMinFat() { return minFat; }
    public void setMinFat(Double minFat) { this.minFat = minFat; }

    public Double getMaxFat() { return maxFat; }
    public void setMaxFat(Double maxFat) { this.maxFat = maxFat; }

    public Double getRatePerLitre() { return ratePerLitre; }
    public void setRatePerLitre(Double ratePerLitre) { this.ratePerLitre = ratePerLitre; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}