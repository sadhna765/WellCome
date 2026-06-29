package dairy_milk_backend.controller;

import dairy_milk_backend.entity.Scheme;
import dairy_milk_backend.service.SchemeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schemes")
@CrossOrigin(origins = "*")
public class SchemeController {

    private final SchemeService schemeService;

    public SchemeController(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @GetMapping
    public List<Scheme> getAll() {
        return schemeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scheme> getById(@PathVariable Long id) {
        return ResponseEntity.ok(schemeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Scheme> create(@RequestBody Scheme scheme) {
        return ResponseEntity.ok(schemeService.create(scheme));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Scheme> update(
            @PathVariable Long id,
            @RequestBody Scheme scheme) {

        return ResponseEntity.ok(
                schemeService.update(id, scheme));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        schemeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}