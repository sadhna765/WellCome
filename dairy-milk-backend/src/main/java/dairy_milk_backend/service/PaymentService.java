package dairy_milk_backend.service;

import dairy_milk_backend.entity.Payment;
import dairy_milk_backend.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public List<Payment> getAll() {
        return repository.findAll();
    }

    public Payment save(Payment payment) {
        return repository.save(payment);
    }
}