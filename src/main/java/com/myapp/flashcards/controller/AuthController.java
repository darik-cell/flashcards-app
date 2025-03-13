package com.myapp.flashcards.controller;

import com.myapp.flashcards.dto.AuthRequest;
import com.myapp.flashcards.dto.AuthResponse;
import com.myapp.flashcards.dto.RegisterRequest;
import com.myapp.flashcards.model.User;
import com.myapp.flashcards.repository.UserRepository;
import com.myapp.flashcards.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @PostMapping("/register")
  public String register(@RequestBody RegisterRequest request) {
    if(userRepository.findByEmail(request.getEmail()).isPresent()) {
      return "Email is already taken!";
    }
    User user = new User();
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);
    return "User registered successfully!";
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody AuthRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    String token = jwtUtil.generateJwtToken(request.getEmail());
    return new AuthResponse(token);
  }
}
