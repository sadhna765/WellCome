
package dairy_milk_backend.repository;

import dairy_milk_backend.entity.Rate;
import dairy_milk_backend.entity.MilkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {

    List<Rate> findByActiveTrueOrderByMilkTypeAscMinFatAsc();

    List<Rate> findByMilkTypeAndActiveTrueOrderByMinFatAsc(MilkType milkType);

    @Query("SELECT r FROM Rate r " +
           "WHERE r.milkType = :milkType " +
           "AND :fat >= r.minFat AND :fat <= r.maxFat " +
           "AND r.active = true")
    Optional<Rate> lookup(@Param("milkType") MilkType milkType, @Param("fat") Double fat);
}