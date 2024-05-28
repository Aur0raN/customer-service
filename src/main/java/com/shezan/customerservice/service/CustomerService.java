package com.shezan.customerservice.service;

import com.shezan.customerservice.dao.CustomerDao;
import com.shezan.customerservice.dao.CustomerHistoryDao;
import com.shezan.customerservice.feign.InventoryInterface;
import com.shezan.customerservice.model.Customer;
import com.shezan.customerservice.model.CustomerHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerDao customerDao;


    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Autowired
    InventoryInterface inventoryInterface;

    @Autowired
    CustomerHistoryDao customerHistoryDao;

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

    public ResponseEntity<String> borrowItem(int itemId, int customerId) {
        Optional<Customer> customerCheck = customerDao.findById(customerId);
        if (customerCheck.isPresent()) {
            Customer customer = customerCheck.get();
            ResponseEntity<String> response = inventoryInterface.borrowItem(itemId,customerId);
            if (response.getStatusCode() == HttpStatus.OK) {//This means the item has been successfully borrowed
                CustomerHistory historyEntry = CustomerHistory.builder().customer(customer).itemId(itemId).borrowedAt(LocalDateTime.now()).build();
                customerHistoryDao.save(historyEntry);
                return new ResponseEntity<>("Customer successfully borrowed item " + itemId, HttpStatus.OK);
            }
            else if (response.getStatusCode() == HttpStatus.CONFLICT) {
                return new ResponseEntity<>("Item has been borrowed already", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
            }

        }else {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> returnItem(int itemId, int customerId) {
        Optional<Customer> customerCheck = customerDao.findById(customerId);
        if (customerCheck.isPresent()) {
            Customer customer = customerCheck.get();
            ResponseEntity<String> response = inventoryInterface.returnItem(itemId,customerId);
            if (response.getStatusCode() == HttpStatus.OK) {
                Optional<CustomerHistory> historyCheck = customerHistoryDao.findByItemIdAndCustomerIdAndReturnedAtIsNull(itemId,customerId);
                if (historyCheck.isPresent()) {
                    CustomerHistory historyEntry = historyCheck.get();
                    historyEntry.setReturnedAt(LocalDateTime.now());
                    customerHistoryDao.save(historyEntry);
                    return new ResponseEntity<>("Customer successfully returned item " + itemId, HttpStatus.OK);
                }  else {
                    return new ResponseEntity<>("Customer history not found", HttpStatus.NOT_FOUND);
                }

            } else {
                return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
            }
        } else{
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<List<CustomerHistory>> getCustomerHistory(int customerId) {
        Optional<List<CustomerHistory>> existingCustomerCheck =customerHistoryDao.findByCustomerId(customerId);
        if (existingCustomerCheck.isPresent()) {
            List<CustomerHistory> customerHistoryList = existingCustomerCheck.get();
            return new ResponseEntity<>(customerHistoryList, HttpStatus.OK);

        }else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
