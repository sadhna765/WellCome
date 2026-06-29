package dairy_milk_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "settings")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dairyName;      // dairy ka naam
    private String ownerName;      // malik ka naam
    private String contact;        // phone number
    private String address;        // address
    private Double defaultRate;    // default milk rate (Rs/L)
    private String currency;       // jaise "Rs" / "INR"
}