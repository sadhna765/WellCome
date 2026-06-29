package dairy_milk_backend.service;

import dairy_milk_backend.entity.Scheme;
import dairy_milk_backend.repository.SchemeRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SchemeService {

    private final SchemeRepository repository;

    public SchemeService(SchemeRepository repository) {
        this.repository = repository;
    }

    public List<Scheme> getAll() {
        return repository.findAll();
    }

    public Scheme getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scheme not found: " + id));
    }

    public Scheme create(Scheme scheme) {
        return repository.save(scheme);
    }

    public Scheme update(Long id, Scheme updated) {
        Scheme existing = getById(id);
        existing.setSchemeName(updated.getSchemeName());
        existing.setSchemeType(updated.getSchemeType());
        existing.setDescription(updated.getDescription());
        existing.setValueType(updated.getValueType());
        existing.setValue(updated.getValue());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());
        existing.setActive(updated.isActive());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}