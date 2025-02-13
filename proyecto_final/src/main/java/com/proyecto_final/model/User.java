package com.proyecto_final.model;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Schema(description = "Entidad que representa un usuario dentro del sistema")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del usuario", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre de usuario único", example = "juanperez")
    private String username;

    @Column(nullable = false)
    @Schema(description = "Contraseña del usuario", example = "password123")
    private String password;

    @Column(nullable = false)
    @Schema(description = "Rol del usuario (ejemplo: ADMIN, USER)", example = "USER")
    private String role;

    @ManyToMany(mappedBy = "usuarios")
    @Schema(description = "Motos asociadas al usuario")
    private Set<Moto> motos = new HashSet<>();
}
