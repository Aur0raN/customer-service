package com.shezan.customerservice.dao;

import com.shezan.customerservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer, Integer> {

}
