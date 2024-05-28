package com.shezan.customerservice.dao;

import com.shezan.customerservice.model.CustomerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerHistoryDao extends JpaRepository<CustomerHistory, Integer> {
    Optional<CustomerHistory> findByItemIdAndCustomerIdAndReturnedAtIsNull(Integer itemId, Integer customerId);
    Optional<List<CustomerHistory>> findByCustomerId(Integer customerId);

}
