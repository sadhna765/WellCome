package dairy_milk_backend.repository;

import dairy_milk_backend.entity.MilkCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MilkCollectionRepository extends JpaRepository<MilkCollection, Long> {

    @Query("SELECT COALESCE(SUM(m.quantity), 0) FROM MilkCollection m")
    Double sumQuantity();

    List<MilkCollection> findByCollectionDate(LocalDate date);

    List<MilkCollection> findByCollectionDateAndShift(LocalDate date, MilkCollection.Shift shift);

    List<MilkCollection> findByFarmerId(Long farmerId);

    long countByCollectionDate(LocalDate date);

    @Query("SELECT COALESCE(SUM(m.quantity), 0) FROM MilkCollection m WHERE m.collectionDate = :date")
    Double getTotalMilkByDate(@Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(m.amount), 0) FROM MilkCollection m WHERE m.collectionDate = :date")
    Double getTotalAmountByDate(@Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(m.quantity), 0) FROM MilkCollection m WHERE m.collectionDate BETWEEN :start AND :end")
    Double getTotalMilkBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(SUM(m.amount), 0) FROM MilkCollection m WHERE m.collectionDate BETWEEN :start AND :end")
    Double getTotalAmountBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
