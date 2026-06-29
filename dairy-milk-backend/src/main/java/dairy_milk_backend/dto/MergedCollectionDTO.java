package dairy_milk_backend.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MergedCollectionDTO {
    private Long farmerId;
    private String farmerName;
    private LocalDate collectionDate;
    private String shift; // ← ADDED

    private double morningQuantity;
    private double morningFat;
    private double morningSnf;
    private double morningRate;
    private double morningAmount;

    private double eveningQuantity;
    private double eveningFat;
    private double eveningSnf;
    private double eveningRate;
    private double eveningAmount;

    private double totalQuantity;
    private double totalAmount;
    private double avgFat;
    private double avgSnf;
}