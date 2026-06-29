package dairy_milk_backend.repository;

import dairy_milk_backend.entity.Advance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface AdvanceRepository extends JpaRepository<Advance, Long> {

    List<Advance> findByFarmerId(Long farmerId);

    List<Advance> findByAdvanceDate(LocalDate date);

    // Farmer ka total pending balance
    @Query("SELECT COALESCE(SUM(a.balance), 0) FROM Advance a WHERE a.farmer.id = :farmerId")
    Double getPendingBalanceByFarmer(Long farmerId);

    // Total advance diya gaya (sab farmers ka)
    @Query("SELECT COALESCE(SUM(a.amount), 0) FROM Advance a")
    Double getTotalAdvanceGiven();
}