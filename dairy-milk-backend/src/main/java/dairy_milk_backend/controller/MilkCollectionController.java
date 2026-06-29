package dairy_milk_backend.controller;

import dairy_milk_backend.dto.MergedCollectionDTO;
import dairy_milk_backend.entity.MilkCollection;
import dairy_milk_backend.service.MilkCollectionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/milk-collection")
@CrossOrigin(origins = "*")
public class MilkCollectionController {

    private final MilkCollectionService milkCollectionService;

    public MilkCollectionController(MilkCollectionService milkCollectionService) {
        this.milkCollectionService = milkCollectionService;
    }

    // POST - Naya entry
    @PostMapping("/{farmerId}")
    public ResponseEntity<MilkCollection> save(@PathVariable Long farmerId,
                                               @RequestBody MilkCollection milkCollection) {
        return ResponseEntity.ok(milkCollectionService.saveMilkCollection(farmerId, milkCollection));
    }
    
    // ✅ Badlo
    @GetMapping("/{id}")
    public ResponseEntity<MilkCollection> getById(@PathVariable Long id) {
        return ResponseEntity.ok(milkCollectionService.getById(id));
    }
    
    // GET - Aaj ki entries
    @GetMapping("/today")
    public ResponseEntity<List<MilkCollection>> getToday() {
        return ResponseEntity.ok(milkCollectionService.getTodayCollections());
    }

    // GET - Date wise
    @GetMapping("/date/{date}")
    public ResponseEntity<List<MilkCollection>> getByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(milkCollectionService.getCollectionsByDate(date));
    }

    @GetMapping("/merged/date/{date}")
    public ResponseEntity<List<MergedCollectionDTO>> getMergedByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(milkCollectionService.getMergedCollectionsByDate(date));
    }

    @GetMapping("/merged")
    public ResponseEntity<List<MergedCollectionDTO>> getAllMerged() {
        return ResponseEntity.ok(milkCollectionService.getAllMergedCollections());
    }

    // GET - Date + Shift wise
    @GetMapping("/date/{date}/shift/{shift}")
    public ResponseEntity<List<MilkCollection>> getByDateAndShift(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable MilkCollection.Shift shift) {
        return ResponseEntity.ok(milkCollectionService.getCollectionsByDateAndShift(date, shift));
    }

    // GET - Farmer wise
    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<MilkCollection>> getByFarmer(@PathVariable Long farmerId) {
        return ResponseEntity.ok(milkCollectionService.getCollectionsByFarmer(farmerId));
    }

    // GET - Daily Summary
    @GetMapping("/summary/{date}")
    public ResponseEntity<MilkCollectionService.DailySummary> getSummary(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(milkCollectionService.getDailySummary(date));
    }

    // PUT - Update entry
    @PutMapping("/{id}")
    public ResponseEntity<MilkCollection> update(@PathVariable Long id,
                                                  @RequestBody MilkCollection milkCollection) {
        return ResponseEntity.ok(milkCollectionService.updateMilkCollection(id, milkCollection));
    }

    // DELETE - Entry delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        milkCollectionService.deleteMilkCollection(id);
        return ResponseEntity.noContent().build();
    }
}
