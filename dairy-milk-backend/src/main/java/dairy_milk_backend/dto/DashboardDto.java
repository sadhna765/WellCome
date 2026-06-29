package dairy_milk_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardDto {

    private Long totalFarmers;
    private Long activeFarmers;
    private Double totalMilkCollection;
    private Double totalPayments;
    private Double todayMilk;
    private Double todayAmount;
    private Long todayEntries;
    private Double monthMilk;
    private Double monthAmount;
    private Double pendingPayments;

    public Long getTotalFarmers() {
        return totalFarmers;
    }

    public void setTotalFarmers(Long totalFarmers) {
        this.totalFarmers = totalFarmers;
    }

    public Long getActiveFarmers() {
        return activeFarmers;
    }

    public void setActiveFarmers(Long activeFarmers) {
        this.activeFarmers = activeFarmers;
    }

    public Double getTotalMilkCollection() {
        return totalMilkCollection;
    }

    public void setTotalMilkCollection(Double totalMilkCollection) {
        this.totalMilkCollection = totalMilkCollection;
    }

    public Double getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(Double totalPayments) {
        this.totalPayments = totalPayments;
    }

    public Double getTodayMilk() {
        return todayMilk;
    }

    public void setTodayMilk(Double todayMilk) {
        this.todayMilk = todayMilk;
    }

    public Double getTodayAmount() {
        return todayAmount;
    }

    public void setTodayAmount(Double todayAmount) {
        this.todayAmount = todayAmount;
    }

    public Long getTodayEntries() {
        return todayEntries;
    }

    public void setTodayEntries(Long todayEntries) {
        this.todayEntries = todayEntries;
    }

    public Double getMonthMilk() {
        return monthMilk;
    }

    public void setMonthMilk(Double monthMilk) {
        this.monthMilk = monthMilk;
    }

    public Double getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(Double monthAmount) {
        this.monthAmount = monthAmount;
    }

    public Double getPendingPayments() {
        return pendingPayments;
    }

    public void setPendingPayments(Double pendingPayments) {
        this.pendingPayments = pendingPayments;
    }
}
