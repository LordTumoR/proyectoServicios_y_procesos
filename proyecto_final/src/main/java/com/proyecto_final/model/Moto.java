package com.proyecto_final.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "motos")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Schema(description = "Entidad que representa una moto dentro del sistema")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la moto", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Modelo de la moto", example = "Cota 311")
    private String modelo;

    @Column(nullable = false)
    @Schema(description = "Marca de la moto", example = "Montesa")
    private String marca;

    @Column(nullable = false)
    @Schema(description = "Cilindrada de la moto en cc", example = "125")
    private String Cilindrada;

    @Column(nullable = false)
    @Schema(description = "Número de caballos de fuerza de la moto", example = "30")
    private int Cv;

    @Column(nullable = false)
    @Schema(description = "Precio de la moto en euros", example = "5000")
    private int Precio;

    @ManyToMany
    @JoinTable(
        name = "moto_concesionario",
        joinColumns = @JoinColumn(name = "moto_id"),
        inverseJoinColumns = @JoinColumn(name = "concesionario_id")
    )
    @Schema(description = "Concesionarios asociados a esta moto")
    private Set<Concesionario> concesionarios;

    @ManyToMany
    @JoinTable(
        name = "usuario_moto",
        joinColumns = @JoinColumn(name = "moto_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @JsonIgnore
    @Schema(description = "Usuarios que tienen asociada esta moto")
    private Set<User> usuarios = new HashSet<>();
}
