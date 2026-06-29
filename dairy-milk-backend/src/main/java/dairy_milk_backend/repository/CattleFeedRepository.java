package dairy_milk_backend.repository;

import dairy_milk_backend.entity.CattleFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CattleFeedRepository extends JpaRepository<CattleFeed, Long> {
    List<CattleFeed> findByFarmerId(Long farmerId);
    List<CattleFeed> findByFarmerIdAndFeedDateBetween(Long farmerId, LocalDate from, LocalDate to);
    List<CattleFeed> findByFeedDate(LocalDate date);
}