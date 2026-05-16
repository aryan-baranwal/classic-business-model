package com.classicmodels.repository;

import com.classicmodels.entity.Payment;
import com.classicmodels.entity.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {
}

