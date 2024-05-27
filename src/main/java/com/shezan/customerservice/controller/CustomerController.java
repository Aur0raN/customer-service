package com.shezan.customerservice.controller;


import com.shezan.customerservice.model.Customer;
import com.shezan.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("allCustomers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("getCustomer")
    public ResponseEntity<Customer> getCustomer(@RequestParam int id) {
        return customerService.getCustomer(id);
    }

    @PostMapping("create")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteCustomer(@RequestParam int id) {
        return customerService.deleteCustomer(id);
    }

    @PutMapping("update")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }


}
