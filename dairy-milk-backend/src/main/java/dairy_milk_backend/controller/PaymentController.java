package dairy_milk_backend.controller;

import dairy_milk_backend.entity.Payment;
import dairy_milk_backend.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @GetMapping
    public List<Payment> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Payment save(@RequestBody Payment payment) {
        return service.save(payment);
    }
}