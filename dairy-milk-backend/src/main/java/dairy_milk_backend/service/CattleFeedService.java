package dairy_milk_backend.service;

import dairy_milk_backend.entity.CattleFeed;
import dairy_milk_backend.entity.Farmer;
import dairy_milk_backend.repository.CattleFeedRepository;
import dairy_milk_backend.repository.FarmerRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class CattleFeedService {

    private final CattleFeedRepository cattleFeedRepository;
    private final FarmerRepository farmerRepository;

    public CattleFeedService(CattleFeedRepository cattleFeedRepository,
                              FarmerRepository farmerRepository) {
        this.cattleFeedRepository = cattleFeedRepository;
        this.farmerRepository = farmerRepository;
    }

    // Save
    public CattleFeed save(Long farmerId, CattleFeed cattleFeed) {
        Farmer farmer = farmerRepository.findById(farmerId)
            .orElseThrow(() -> new RuntimeException("Farmer not found: " + farmerId));

        cattleFeed.setFarmer(farmer);

        // Amount calculate
        if (cattleFeed.getQuantity() != null && cattleFeed.getRate() != null) {
            cattleFeed.setAmount(
                Math.round(cattleFeed.getQuantity() * cattleFeed.getRate() * 100.0) / 100.0
            );
        }

        return cattleFeedRepository.save(cattleFeed);
    }

    // Farmer wise saari entries
    public List<CattleFeed> getByFarmer(Long farmerId) {
        return cattleFeedRepository.findByFarmerId(farmerId);
    }

    // Farmer wise date range
    public List<CattleFeed> getByFarmerAndDateRange(Long farmerId, LocalDate from, LocalDate to) {
        return cattleFeedRepository.findByFarmerIdAndFeedDateBetween(farmerId, from, to);
    }

    // Saari entries
    public List<CattleFeed> getAll() {
        return cattleFeedRepository.findAll();
    }

    // Delete
    public void delete(Long id) {
        cattleFeedRepository.deleteById(id);
    }
}