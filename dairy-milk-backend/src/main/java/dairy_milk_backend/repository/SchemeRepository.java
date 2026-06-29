package dairy_milk_backend.repository;

import dairy_milk_backend.entity.Scheme;   // entity jis package me hai, wahi import karo
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {

    List<Scheme> findByActiveTrue();
    List<Scheme> findBySchemeType(String schemeType);
}