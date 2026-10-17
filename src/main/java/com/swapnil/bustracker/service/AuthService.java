package com.swapnil.bustracker.service;

import com.swapnil.bustracker.config.JwtUtil;
import com.swapnil.bustracker.dto.LoginResponse;
import com.swapnil.bustracker.dto.UserDTO;
import com.swapnil.bustracker.model.User;
import com.swapnil.bustracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User register(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email is already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public LoginResponse login(User user){
        User existing = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!existing.getRole().name().equalsIgnoreCase(user.getRole().name())) {
            throw new RuntimeException("Invalid role selected");
        }
        if(!passwordEncoder.matches(user.getPassword(), existing.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(existing.getEmail());
        UserDTO userDTO = new UserDTO(existing.getId(),existing.getName(), existing.getEmail(),existing.getRole().name());
        return new LoginResponse(token ,userDTO );
    }
}
