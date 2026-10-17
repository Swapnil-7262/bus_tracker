package com.swapnil.bustracker.service;

import com.swapnil.bustracker.config.JwtUtil;
import com.swapnil.bustracker.dto.LoginResponse;
import com.swapnil.bustracker.dto.UserDTO;
import com.swapnil.bustracker.model.Bus;
import com.swapnil.bustracker.model.Role;
import com.swapnil.bustracker.model.User;
import com.swapnil.bustracker.repository.BusRepository;
import com.swapnil.bustracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final BusRepository busRepository;

//    public UserService(UserRepository userRepository,
//                           PasswordEncoder passwordEncoder,
//                           JwtUtil jwtUtil) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtUtil = jwtUtil;
//    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }



    public List<User> getUserByRole(Role role){
        if(role == null){
            throw new RuntimeException("Invalid Request");
        }
        List<User> users = userRepository.findByRole(role);

        if (users.isEmpty()) {
            throw new RuntimeException("No users found for role: " + role);
        }
        return users;
    }
    public void updateUserBus(String email, Long busId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Selected bus does not exist in database"));

        user.setAssignedBus(bus);
        userRepository.save(user);
    }

}
