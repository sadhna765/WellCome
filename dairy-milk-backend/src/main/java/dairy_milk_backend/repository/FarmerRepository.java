package dairy_milk_backend.repository;

import dairy_milk_backend.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {

    long countByStatus(String status);
}
