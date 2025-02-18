package com.imaginer.project.controller;

import com.imaginer.project.config.JwtUtil;
import com.imaginer.project.dto.AuthRequest;
import com.imaginer.project.dto.AuthResponse;
import com.imaginer.project.entity.User;
import com.imaginer.project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://imaginer-frontend.eu-north-1.elasticbeanstalk.com")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final UserService userService;

  public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
    User user = userService.registerUser(request.getUsername(), request.getPassword());
    return ResponseEntity.ok(new AuthResponse(user.getUsername(), "User Registered Successfully"));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
    System.out.println("Login request received for user: " + request.getUsername());
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    final String jwt = jwtUtil.generateToken(request.getUsername());
    return ResponseEntity.ok(new AuthResponse(request.getUsername(), jwt));
  }
}
