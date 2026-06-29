package dairy_milk_backend.repository;

import dairy_milk_backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT COALESCE(SUM(p.netAmount), 0) FROM Payment p")
    Double sumAmount();

    @Query("SELECT COALESCE(SUM(p.netAmount), 0) FROM Payment p WHERE p.status = 'PENDING'")
    Double getPendingPaymentsTotal();

    @Query("SELECT COALESCE(SUM(p.netAmount), 0) FROM Payment p")
    Double getTotalPayments();
}
