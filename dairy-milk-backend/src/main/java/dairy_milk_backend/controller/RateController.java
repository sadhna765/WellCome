package dairy_milk_backend.controller;

import dairy_milk_backend.entity.Rate;
import dairy_milk_backend.entity.MilkType;
import dairy_milk_backend.service.RateService;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rates")
@CrossOrigin(origins = "http://localhost:4200")
public class RateController {

    private final RateService service;

    public RateController(RateService service) {
        this.service = service;
    }

    @GetMapping
    public List<Rate> getAll(@RequestParam(required = false) MilkType milkType) {
        return milkType == null ? service.getAll() : service.getByType(milkType);
    }

    @PostMapping
    public Rate create(@RequestBody Rate rate) {
        return service.save(rate);
    }

    @PutMapping("/{id}")
    public Rate update(@PathVariable Long id, @RequestBody Rate rate) {
        return service.update(id, rate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // GET /api/rates/lookup?milkType=COW&fat=4.2
    @GetMapping("/lookup")
    public Map<String, Object> lookup(@RequestParam MilkType milkType,
                                      @RequestParam Double fat) {
        Double rate = service.lookupRate(milkType, fat);
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("milkType", milkType);
        res.put("fat", fat);
        res.put("rate", rate);
        return res;
    }
}