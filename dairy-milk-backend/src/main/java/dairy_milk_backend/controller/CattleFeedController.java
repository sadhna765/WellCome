package dairy_milk_backend.controller;

import dairy_milk_backend.entity.CattleFeed;
import dairy_milk_backend.service.CattleFeedService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cattle-feed")
@CrossOrigin(origins = "*")
public class CattleFeedController {

    private final CattleFeedService cattleFeedService;

    public CattleFeedController(CattleFeedService cattleFeedService) {
        this.cattleFeedService = cattleFeedService;
    }

    // POST - Save
    @PostMapping("/{farmerId}")
    public ResponseEntity<CattleFeed> save(@PathVariable Long farmerId,
                                           @RequestBody CattleFeed cattleFeed) {
        return ResponseEntity.ok(cattleFeedService.save(farmerId, cattleFeed));
    }

    // GET - Farmer wise
    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<CattleFeed>> getByFarmer(@PathVariable Long farmerId) {
        return ResponseEntity.ok(cattleFeedService.getByFarmer(farmerId));
    }

    // GET - Farmer + Date range
    @GetMapping("/farmer/{farmerId}/range")
    public ResponseEntity<List<CattleFeed>> getByFarmerAndRange(
            @PathVariable Long farmerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(cattleFeedService.getByFarmerAndDateRange(farmerId, from, to));
    }

    // GET - Saari entries
    @GetMapping
    public ResponseEntity<List<CattleFeed>> getAll() {
        return ResponseEntity.ok(cattleFeedService.getAll());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cattleFeedService.delete(id);
        return ResponseEntity.noContent().build();
    }
}