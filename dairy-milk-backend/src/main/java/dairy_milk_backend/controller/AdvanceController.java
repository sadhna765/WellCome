package dairy_milk_backend.controller;

import dairy_milk_backend.entity.Advance;
import dairy_milk_backend.service.AdvanceService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/advances")
@CrossOrigin(origins = "http://localhost:4200")
public class AdvanceController {

    private final AdvanceService advanceService;

    public AdvanceController(AdvanceService advanceService) {
        this.advanceService = advanceService;
    }

    @PostMapping("/farmer/{farmerId}")
    public Advance create(@PathVariable Long farmerId, @RequestBody Advance advance) {
        return advanceService.saveAdvance(farmerId, advance);
    }

    @GetMapping
    public List<Advance> getAll() {
        return advanceService.getAllAdvances();
    }

    @GetMapping("/farmer/{farmerId}")
    public List<Advance> getByFarmer(@PathVariable Long farmerId) {
        return advanceService.getAdvancesByFarmer(farmerId);
    }

    @GetMapping("/{id}")
    public Advance getById(@PathVariable Long id) {
        return advanceService.getById(id);
    }

    @GetMapping("/farmer/{farmerId}/balance")
    public Double getPendingBalance(@PathVariable Long farmerId) {
        return advanceService.getPendingBalance(farmerId);
    }

    @PutMapping("/{id}")
    public Advance update(@PathVariable Long id, @RequestBody Advance advance) {
        return advanceService.updateAdvance(id, advance);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        advanceService.deleteAdvance(id);
    }
}