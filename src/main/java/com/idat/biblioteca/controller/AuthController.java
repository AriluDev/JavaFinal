package com.idat.biblioteca.controller;

import com.idat.biblioteca.model.Rol;
import com.idat.biblioteca.model.Usuario;
import com.idat.biblioteca.repository.RolRepository;
import com.idat.biblioteca.repository.UsuarioRepository;
import com.idat.biblioteca.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository repository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> request) {
        // Verificar si el correo ya existe
        String correo = (String) request.get("correo");
        if (repository.findByCorreo(correo).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }

        // Determinar el rol: ROLE_ADMIN si esAdmin=true, sino ROLE_USUARIO
        boolean esAdmin = Boolean.TRUE.equals(request.get("esAdmin"));
        String nombreRol = esAdmin ? "ROLE_ADMIN" : "ROLE_USUARIO";
        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombreRol));

        // Crear y guardar el usuario
        Usuario usuario = new Usuario();
        usuario.setNombres((String) request.get("nombres"));
        usuario.setCorreo(correo);
        usuario.setPassword(passwordEncoder.encode((String) request.get("password")));
        usuario.setRoles(Set.of(rol));

        repository.save(usuario);
        return ResponseEntity.ok("Usuario registrado con éxito como " + nombreRol);
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