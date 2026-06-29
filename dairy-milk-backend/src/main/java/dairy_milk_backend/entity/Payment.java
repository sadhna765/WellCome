package dairy_milk_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long farmerId;

    private String farmerName;

    private LocalDate fromDate;

    private LocalDate toDate;

    private Double milkAmount;

    private Double feedDeduction;

    private Double advanceDeduction;

    private Double netAmount;

    private LocalDate paymentDate;

    private String status;

    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Long farmerId) {
        this.farmerId = farmerId;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Double getMilkAmount() {
        return milkAmount;
    }

    public void setMilkAmount(Double milkAmount) {
        this.milkAmount = milkAmount;
    }

    public Double getFeedDeduction() {
        return feedDeduction;
    }

    public void setFeedDeduction(Double feedDeduction) {
        this.feedDeduction = feedDeduction;
    }

    public Double getAdvanceDeduction() {
        return advanceDeduction;
    }

    public void setAdvanceDeduction(Double advanceDeduction) {
        this.advanceDeduction = advanceDeduction;
    }

    public Double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}