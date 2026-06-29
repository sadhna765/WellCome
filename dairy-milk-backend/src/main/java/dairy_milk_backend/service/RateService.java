package dairy_milk_backend.service;

import dairy_milk_backend.entity.Rate;
import dairy_milk_backend.entity.MilkType;
import dairy_milk_backend.repository.RateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateService {

    private final RateRepository repo;

    public RateService(RateRepository repo) {
        this.repo = repo;
    }

    public List<Rate> getAll() {
        return repo.findByActiveTrueOrderByMilkTypeAscMinFatAsc();
    }

    public List<Rate> getByType(MilkType type) {
        return repo.findByMilkTypeAndActiveTrueOrderByMinFatAsc(type);
    }

    public Rate save(Rate rate) {
        if (rate.getActive() == null) rate.setActive(true);
        validate(rate);
        return repo.save(rate);
    }

    public Rate update(Long id, Rate data) {
        Rate r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rate not found: " + id));
        r.setMilkType(data.getMilkType());
        r.setMinFat(data.getMinFat());
        r.setMaxFat(data.getMaxFat());
        r.setRatePerLitre(data.getRatePerLitre());
        if (data.getActive() != null) r.setActive(data.getActive());
        validate(r);
        return repo.save(r);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    /** Returns rate per litre for a given milk type + fat %, or 0.0 if no slab matches. */
    public Double lookupRate(MilkType type, Double fat) {
        return repo.lookup(type, fat)
                .map(Rate::getRatePerLitre)
                .orElse(0.0);
    }

    private void validate(Rate r) {
        if (r.getMinFat() == null || r.getMaxFat() == null || r.getMinFat() >= r.getMaxFat()) {
            throw new RuntimeException("minFat must be less than maxFat");
        }
        if (r.getRatePerLitre() == null || r.getRatePerLitre() <= 0) {
            throw new RuntimeException("ratePerLitre must be greater than 0");
        }
    }
}