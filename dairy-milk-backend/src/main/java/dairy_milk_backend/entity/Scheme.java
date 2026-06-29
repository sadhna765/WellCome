package dairy_milk_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "schemes")
public class Scheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String schemeName;

    @Column(nullable = false)
    private String schemeType;     // INCENTIVE | LOAN | ADVANCE | SUBSIDY | FEED

    @Column(length = 500)
    private String description;

    private String valueType;      // FIXED | PERCENTAGE

    @Column(name = "scheme_value") // 'value' SQL me reserved word hai, isliye column alag
    private Double value;

    private LocalDate startDate;
    private LocalDate endDate;

    private boolean active = true;

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSchemeName() { return schemeName; }
    public void setSchemeName(String schemeName) { this.schemeName = schemeName; }

    public String getSchemeType() { return schemeType; }
    public void setSchemeType(String schemeType) { this.schemeType = schemeType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getValueType() { return valueType; }
    public void setValueType(String valueType) { this.valueType = valueType; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
