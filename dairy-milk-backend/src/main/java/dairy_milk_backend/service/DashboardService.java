package dairy_milk_backend.service;

import dairy_milk_backend.dto.DashboardDto;
import dairy_milk_backend.repository.FarmerRepository;
import dairy_milk_backend.repository.MilkCollectionRepository;
import dairy_milk_backend.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DashboardService {

    private final FarmerRepository farmerRepository;
    private final MilkCollectionRepository milkCollectionRepository;
    private final PaymentRepository paymentRepository;

    public DashboardService(FarmerRepository farmerRepository,
                            MilkCollectionRepository milkCollectionRepository,
                            PaymentRepository paymentRepository) {
        this.farmerRepository = farmerRepository;
        this.milkCollectionRepository = milkCollectionRepository;
        this.paymentRepository = paymentRepository;
    }

    public DashboardDto getDashboardStats() {
        log.info("Building dashboard stats");

        DashboardDto stats = new DashboardDto();
        stats.setTotalFarmers(farmerRepository.count());
        stats.setActiveFarmers(farmerRepository.countByStatus("ACTIVE"));
        stats.setTotalMilkCollection(value(milkCollectionRepository.sumQuantity()));
        stats.setTotalPayments(value(paymentRepository.getTotalPayments()));

        return stats;
    }

    private double value(Double number) {
        return number != null ? number : 0.0;
    }
}
