package dairy_milk_backend.repository;

import dairy_milk_backend.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {
    // Sab kuch JpaRepository se mil jata hai — extra method ki zaroorat nahi
}