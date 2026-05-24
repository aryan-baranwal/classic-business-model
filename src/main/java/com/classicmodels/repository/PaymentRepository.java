package com.classicmodels.repository;
import com.classicmodels.entity.Payment;
import com.classicmodels.entity.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {

    // MONTHLY REVENUE REPORT

    @Query("""
           SELECT
               FUNCTION(
                   'DATE_FORMAT',
                   p.paymentDate,
                   '%Y-%m'
               ),
               SUM(p.amount),
               MIN(p.paymentDate),
               MAX(p.paymentDate)
           FROM Payment p
           GROUP BY FUNCTION(
               'DATE_FORMAT',
               p.paymentDate,
               '%Y-%m'
           )
           ORDER BY FUNCTION(
               'DATE_FORMAT',
               p.paymentDate,
               '%Y-%m'
           )
           """)
    List<Object[]> findMonthlyRevenue();
}
