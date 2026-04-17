package com.idat.biblioteca.controller;

import com.idat.biblioteca.model.Usuario;
import com.idat.biblioteca.repository.UsuarioRepository;
import com.idat.biblioteca.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        repository.save(request);
        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.get("correo"), request.get("password"))
        );
        var user = repository.findByCorreo(request.get("correo")).orElseThrow();
        var token = jwtService.generateToken(user);
        return ResponseEntity.ok(Map.of("token", token));
    }
}