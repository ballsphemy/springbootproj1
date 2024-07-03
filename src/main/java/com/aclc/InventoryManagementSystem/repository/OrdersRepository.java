package com.aclc.InventoryManagementSystem.repository;

import com.aclc.InventoryManagementSystem.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByStatus(String status);
    @Query("SELECT o FROM Orders o WHERE o.lastUpdated BETWEEN :startDateTime AND :endDateTime AND o.status = :status")
    List<Orders> findByLastUpdatedBetweenAndStatus(@Param("startDateTime") LocalDateTime startDateTime,
                                                   @Param("endDateTime") LocalDateTime endDateTime,
                                                   @Param("status") String status);
}
