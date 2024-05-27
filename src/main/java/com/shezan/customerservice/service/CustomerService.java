package com.shezan.customerservice.service;

import com.shezan.customerservice.dao.CustomerDao;
import com.shezan.customerservice.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerDao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Customer> getCustomer(int id) {
        Customer customer = customerDao.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    public ResponseEntity<String> addCustomer(Customer customer) {
        customerDao.save(customer);
        return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);

    }

    public ResponseEntity<String> deleteCustomer(Integer id) {
        customerDao.deleteById(id);
        return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> updateCustomer(Customer customer) {
        Optional<Customer> existingCustomerCheck = customerDao.findById(customer.getId());
        if (existingCustomerCheck.isPresent()) {
            Customer existingCustomer = existingCustomerCheck.get();
            existingCustomer.setCustomerName(customer.getCustomerName());
            existingCustomer.setCustomerEmail(customer.getCustomerEmail());
            existingCustomer.setCustomerPhone(customer.getCustomerPhone());
            customerDao.save(existingCustomer);
            return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
    }
}
