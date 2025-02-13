package com.proyecto_final.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto_final.dto.UserLoginDTO;
import com.proyecto_final.dto.UserRegistrationDto;
import com.proyecto_final.model.User;
import com.proyecto_final.repository.UserRepository;
import com.proyecto_final.security.JwtTokenProvider;
import com.proyecto_final.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario autenticarse en el sistema y obtener un token JWT.")
    @ApiResponse(responseCode = "200", description = "Inicio de sesion exitoso. Se devulve el token de autenticacion.")
    @ApiResponse(responseCode = "401", description = "Credenciales incorrectas. No se puede iniciar sesion.")
    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return jwtTokenProvider.generateToken(user);
        } else {
            throw new RuntimeException("Credenciales incorrectas");
        }
    }

    @Operation(summary = "Registrar un usuario", description = "Crea una nueva cuenta de usuario en el sistema.")
    @ApiResponse(responseCode = "200", description = "Usuario registrado con exito.")
    @ApiResponse(responseCode = "400", description = "El nombre de usuario ya esta en uso.")
    @ApiResponse(responseCode = "500", description = "Error interno al intentar registrar el usuario.")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto registrationDTO) {
        try {
            if (userRepository.findByUsername(registrationDTO.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("El nombre de usuario ya está en uso");
            }
            String encodedPassword = new BCryptPasswordEncoder().encode(registrationDTO.getPassword());

            User newUser = new User();
            newUser.setUsername(registrationDTO.getUsername());
            newUser.setPassword(encodedPassword);
            newUser.setRole("USER");

            User savedUser = userService.createUser(newUser);

            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario");
        }
    }
}
