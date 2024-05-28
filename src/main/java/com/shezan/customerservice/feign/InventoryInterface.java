package com.shezan.customerservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("INVENTORY-SERVICE")
public interface InventoryInterface {

    @PostMapping("inventory/borrow")
    public ResponseEntity<String> borrowItem(@RequestParam Integer itemId, @RequestParam Integer customerId);

    @PostMapping("inventory/return")
    public ResponseEntity<String> returnItem(@RequestParam Integer itemId, @RequestParam Integer customerId);


}
