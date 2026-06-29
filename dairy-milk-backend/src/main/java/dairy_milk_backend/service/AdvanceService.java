package dairy_milk_backend.service;

import dairy_milk_backend.entity.Advance;
import dairy_milk_backend.entity.Farmer;
import dairy_milk_backend.repository.AdvanceRepository;
import dairy_milk_backend.repository.FarmerRepository;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class AdvanceService {

    private final AdvanceRepository advanceRepository;
    private final FarmerRepository farmerRepository;

    public AdvanceService(AdvanceRepository advanceRepository,
                          FarmerRepository farmerRepository) {
        this.advanceRepository = advanceRepository;
        this.farmerRepository = farmerRepository;
    }

    // Naya advance entry
    public Advance saveAdvance(Long farmerId, Advance advance) {
        Farmer farmer = farmerRepository.findById(farmerId)
            .orElseThrow(() -> new RuntimeException("Farmer not found: " + farmerId));

        if (advance.getAmount() == null || advance.getAmount() <= 0) {
            throw new IllegalArgumentException("Advance amount valid hona chahiye");
        }

        if (advance.getAdvanceDate() == null) {
            advance.setAdvanceDate(LocalDate.now());
        }

        advance.setFarmer(farmer);
        // Jab advance diya — pura amount recover hona baaki
        advance.setBalance(advance.getAmount());

        return advanceRepository.save(advance);
    }

    public List<Advance> getAllAdvances() {
        return advanceRepository.findAll();
    }

    public List<Advance> getAdvancesByFarmer(Long farmerId) {
        return advanceRepository.findByFarmerId(farmerId);
    }

    public Advance getById(Long id) {
        return advanceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Advance not found: " + id));
    }

    // Farmer ka total pending balance
    public Double getPendingBalance(Long farmerId) {
        Double balance = advanceRepository.getPendingBalanceByFarmer(farmerId);
        return balance != null ? balance : 0.0;
    }

    public Advance updateAdvance(Long id, Advance updated) {
        return advanceRepository.findById(id)
            .map(existing -> {
                existing.setAmount(updated.getAmount());
                existing.setReason(updated.getReason());
                existing.setAdvanceDate(updated.getAdvanceDate());
                return advanceRepository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Advance not found: " + id));
    }

    public void deleteAdvance(Long id) {
        advanceRepository.deleteById(id);
    }

    // ===== AUTO DEDUCTION (payment ke time call hoga) =====
    // Farmer ke pending advances me se amount cut karo, kitna cut hua wo return karo
    public double deductFromAdvances(Long farmerId, double availableAmount) {
        List<Advance> advances = advanceRepository.findByFarmerId(farmerId);
        double totalDeducted = 0.0;

        for (Advance advance : advances) {
            if (availableAmount <= 0) break;
            double bal = advance.getBalance() != null ? advance.getBalance() : 0.0;
            if (bal <= 0) continue;

            double cut = Math.min(bal, availableAmount);
            advance.setBalance(round2(bal - cut));
            advanceRepository.save(advance);

            totalDeducted += cut;
            availableAmount -= cut;
        }
        return round2(totalDeducted);
    }

    private double round2(double n) {
        return Math.round(n * 100.0) / 100.0;
    }
}