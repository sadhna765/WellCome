package dairy_milk_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "milk_collection")
public class MilkCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Shift shift; // MORNING / EVENING

    @Column(nullable = false)
    private LocalDate collectionDate;

    private Double quantity;  // litres
    private Double fat;
    private Double snf;
    private Double rate;      // fetched from Rate Management
    private Double amount;    // quantity * rate

    public enum Shift {
        MORNING, EVENING
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Farmer getFarmer() { return farmer; }
    public void setFarmer(Farmer farmer) { this.farmer = farmer; }

    public Shift getShift() { return shift; }
    public void setShift(Shift shift) { this.shift = shift; }

    public LocalDate getCollectionDate() { return collectionDate; }
    public void setCollectionDate(LocalDate collectionDate) { this.collectionDate = collectionDate; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Double getFat() { return fat; }
    public void setFat(Double fat) { this.fat = fat; }

    public Double getSnf() { return snf; }
    public void setSnf(Double snf) { this.snf = snf; }

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}