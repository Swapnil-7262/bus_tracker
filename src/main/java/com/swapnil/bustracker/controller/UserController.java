package com.swapnil.bustracker.controller;

import com.swapnil.bustracker.dto.ApiResponse;
import com.swapnil.bustracker.dto.LoginResponse;
import com.swapnil.bustracker.model.Role;
import com.swapnil.bustracker.model.User;
import com.swapnil.bustracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/role")

    public ResponseEntity<ApiResponse<List<User>>> userByRole(@RequestParam String role){
        Role userRole;
        try {
            userRole = Role.valueOf(role.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid role: " + role);
        }

        List<User> users = userService.getUserByRole(userRole);
        return ResponseEntity.ok(new ApiResponse<>(true, "Users fetched", users));
    }
    @PutMapping("/change-bus")
    public ResponseEntity<ApiResponse<String>> changeBus(@RequestBody Map<String, Object> payload, Principal principal) {
        try {
            Object busIdObj = payload.get("busId");

            // Check if the value is missing OR is the string "null"
            if (busIdObj == null || "null".equals(busIdObj.toString())) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "Invalid Bus Selection", null));
            }

            Long busId = Long.valueOf(busIdObj.toString());
            userService.updateUserBus(principal.getName(), busId);

            return ResponseEntity.ok(new ApiResponse<>(true, "Bus changed successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }


    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<User>> getMyProfile(Principal principal) {
        try {
            String email = principal.getName();
            User user = userService.getUserByEmail(email); // Ensure this method exists in your UserService
            return ResponseEntity.ok(new ApiResponse<>(true, "Profile fetched", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User not found", null));
        }
    }

}


