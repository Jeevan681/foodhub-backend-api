package com.jeevan.foodhub.controller;

import com.jeevan.foodhub.dto.response.AdminDashboardResponse;
import com.jeevan.foodhub.dto.response.UserSummaryResponse;
import com.jeevan.foodhub.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> dashboard() {
        return ResponseEntity.ok(adminService.getDashboard());
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserSummaryResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserSummaryResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUser(id));
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<UserSummaryResponse>> searchUsers(
            @RequestParam String keyword) {

        return ResponseEntity.ok(adminService.searchUsers(keyword));
    }

    @PutMapping("/users/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {

        adminService.blockUser(id);

        return ResponseEntity.ok("User blocked successfully.");
    }

    @PutMapping("/users/{id}/unblock")
    public ResponseEntity<String> unblockUser(@PathVariable Long id) {

        adminService.unblockUser(id);

        return ResponseEntity.ok("User unblocked successfully.");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        adminService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully.");
    }


}