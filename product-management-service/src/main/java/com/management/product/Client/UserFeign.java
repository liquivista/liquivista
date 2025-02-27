package com.management.product.Client;


import com.management.product.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userFeign" , url = "http://localhost:9803/user-management")
public interface UserFeign {

    @GetMapping("/get-user/{userId}")
    ResponseEntity<UserResponseDto> getUser(@PathVariable("userId") Long userId);

    @GetMapping("/get-all-users")
    ResponseEntity<?> getAllUsers();
}
