package dairy_milk_backend.service;

import dairy_milk_backend.dto.MergedCollectionDTO;
import dairy_milk_backend.entity.MilkCollection;
import dairy_milk_backend.entity.Farmer;
import dairy_milk_backend.repository.MilkCollectionRepository;
import dairy_milk_backend.repository.FarmerRepository;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MilkCollectionService {

    private final MilkCollectionRepository milkCollectionRepository;
    private final FarmerRepository farmerRepository;

    public MilkCollectionService(MilkCollectionRepository milkCollectionRepository,
                                  FarmerRepository farmerRepository) {
        this.milkCollectionRepository = milkCollectionRepository;
        this.farmerRepository = farmerRepository;
    }

    // Naya entry save karo
    public MilkCollection saveMilkCollection(Long farmerId, MilkCollection milkCollection) {
        Farmer farmer = farmerRepository.findById(farmerId)
            .orElseThrow(() -> new RuntimeException("Farmer not found: " + farmerId));

        if (milkCollection.getShift() == null) {
            throw new IllegalArgumentException("Shift is required");
        }

        if (milkCollection.getCollectionDate() == null) {
            throw new IllegalArgumentException("Collection date is required");
        }

        milkCollection.setFarmer(farmer);

        // Amount calculate karo
        if (milkCollection.getQuantity() != null && milkCollection.getRate() != null) {
            double amount = milkCollection.getQuantity() * milkCollection.getRate();
            milkCollection.setAmount(Math.round(amount * 100.0) / 100.0);
        }

        return milkCollectionRepository.save(milkCollection);
    }

    // Aaj ki saari entries
    public List<MilkCollection> getTodayCollections() {
        return milkCollectionRepository.findByCollectionDate(LocalDate.now());
    }
    public MilkCollection getById(Long id) {
        log.info("Fetching collection for ID: {}", id);
        return milkCollectionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Collection not found with id: " + id));
    }

    // Date wise entries
    public List<MilkCollection> getCollectionsByDate(LocalDate date) {
        log.info("Fetching collections for date: {}", date);
        return milkCollectionRepository.findByCollectionDate(date);
    }
    public List<MergedCollectionDTO> getAllMergedCollections() {
        List<MilkCollection> collections = milkCollectionRepository.findAll();
        return mergeCollections(collections);
    }

    public List<MergedCollectionDTO> getMergedCollectionsByDate(LocalDate date) {
        List<MilkCollection> collections = getCollectionsByDate(date);
        return mergeCollections(collections);
    }

    private List<MergedCollectionDTO> mergeCollections(List<MilkCollection> collections) {
    Map<String, MergedCollectionDTO> mergedMap = new LinkedHashMap<>();

    for (MilkCollection collection : collections) {
        Farmer farmer = collection.getFarmer();
        Long farmerId = farmer != null ? farmer.getId() : null;
        if (farmerId == null) continue;

        // farmerId + date + shift = unique key
        String key = farmerId + "|" + collection.getCollectionDate() + "|" + collection.getShift();

        MergedCollectionDTO dto = mergedMap.computeIfAbsent(key, id -> {
            MergedCollectionDTO item = new MergedCollectionDTO();
            item.setFarmerId(farmerId);
            item.setFarmerName(farmer.getName());
            item.setCollectionDate(collection.getCollectionDate());
            item.setShift(collection.getShift().name());
            return item;
        });

        if (collection.getShift() == MilkCollection.Shift.MORNING) {
            mergeMorning(dto, collection);
        } else if (collection.getShift() == MilkCollection.Shift.EVENING) {
            mergeEvening(dto, collection);
        }
    }

    for (MergedCollectionDTO dto : mergedMap.values()) {
        double totalQuantity = dto.getMorningQuantity() + dto.getEveningQuantity();
        dto.setTotalQuantity(round2(totalQuantity));
        dto.setTotalAmount(round2(dto.getMorningAmount() + dto.getEveningAmount()));
        dto.setAvgFat(weightedAverage(
            dto.getMorningFat(), dto.getMorningQuantity(),
            dto.getEveningFat(), dto.getEveningQuantity(),
            totalQuantity
        ));
        dto.setAvgSnf(weightedAverage(
            dto.getMorningSnf(), dto.getMorningQuantity(),
            dto.getEveningSnf(), dto.getEveningQuantity(),
            totalQuantity
        ));
    }

    return new ArrayList<>(mergedMap.values());
}

    // Date + Shift wise entries
    public List<MilkCollection> getCollectionsByDateAndShift(LocalDate date,
                                                              MilkCollection.Shift shift) {
        log.info("Fetching collections for date: {} and shift: {}", date, shift);
        return milkCollectionRepository.findByCollectionDateAndShift(date, shift);
    }

    // Farmer wise entries
    public List<MilkCollection> getCollectionsByFarmer(Long farmerId) {
        return milkCollectionRepository.findByFarmerId(farmerId);
    }

    // Daily summary
    public DailySummary getDailySummary(LocalDate date) {
        Double totalMilk = milkCollectionRepository.getTotalMilkByDate(date);
        Double totalAmount = milkCollectionRepository.getTotalAmountByDate(date);
        return new DailySummary(
            date,
            totalMilk != null ? totalMilk : 0.0,
            totalAmount != null ? totalAmount : 0.0
        );
    }

    // Entry update karo
    public MilkCollection updateMilkCollection(Long id, MilkCollection updated) {
        return milkCollectionRepository.findById(id)
            .map(existing -> {
                existing.setQuantity(updated.getQuantity());
                existing.setFat(updated.getFat());
                existing.setSnf(updated.getSnf());
                existing.setRate(updated.getRate());
                existing.setShift(updated.getShift());
                existing.setCollectionDate(updated.getCollectionDate());

                if (updated.getQuantity() != null && updated.getRate() != null) {
                    double amount = updated.getQuantity() * updated.getRate();
                    existing.setAmount(Math.round(amount * 100.0) / 100.0);
                }

                return milkCollectionRepository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("Entry not found with id: " + id));
    }

    // Entry delete karo
    public void deleteMilkCollection(Long id) {
        milkCollectionRepository.deleteById(id);
    }

    private double value(Double number) {
        return number != null ? number : 0.0;
    }

    private void mergeMorning(MergedCollectionDTO dto, MilkCollection collection) {
        double oldQuantity = dto.getMorningQuantity();
        double addQuantity = value(collection.getQuantity());
        double newQuantity = oldQuantity + addQuantity;
        double addAmount = value(collection.getAmount());

        dto.setMorningFat(weightedAverage(dto.getMorningFat(), oldQuantity, value(collection.getFat()), addQuantity, newQuantity));
        dto.setMorningSnf(weightedAverage(dto.getMorningSnf(), oldQuantity, value(collection.getSnf()), addQuantity, newQuantity));
        dto.setMorningQuantity(round2(newQuantity));
        dto.setMorningAmount(round2(dto.getMorningAmount() + addAmount));
        dto.setMorningRate(rateFromAmount(dto.getMorningAmount(), dto.getMorningQuantity()));
    }

    private void mergeEvening(MergedCollectionDTO dto, MilkCollection collection) {
        double oldQuantity = dto.getEveningQuantity();
        double addQuantity = value(collection.getQuantity());
        double newQuantity = oldQuantity + addQuantity;
        double addAmount = value(collection.getAmount());

        dto.setEveningFat(weightedAverage(dto.getEveningFat(), oldQuantity, value(collection.getFat()), addQuantity, newQuantity));
        dto.setEveningSnf(weightedAverage(dto.getEveningSnf(), oldQuantity, value(collection.getSnf()), addQuantity, newQuantity));
        dto.setEveningQuantity(round2(newQuantity));
        dto.setEveningAmount(round2(dto.getEveningAmount() + addAmount));
        dto.setEveningRate(rateFromAmount(dto.getEveningAmount(), dto.getEveningQuantity()));
    }

    private double weightedAverage(double firstValue, double firstQuantity,
                                   double secondValue, double secondQuantity,
                                   double totalQuantity) {
        if (totalQuantity == 0.0) {
            return 0.0;
        }
        return round2(((firstValue * firstQuantity) + (secondValue * secondQuantity)) / totalQuantity);
    }

    private double round2(double number) {
        return Math.round(number * 100.0) / 100.0;
    }

    private double rateFromAmount(double amount, double quantity) {
        if (quantity == 0.0) {
            return 0.0;
        }
        return round2(amount / quantity);
    }

    // Inner class for daily summary response
    public static class DailySummary {
        public LocalDate date;
        public Double totalMilk;
        public Double totalAmount;

        public DailySummary(LocalDate date, Double totalMilk, Double totalAmount) {
            this.date = date;
            this.totalMilk = totalMilk;
            this.totalAmount = totalAmount;
        }
    }
}
