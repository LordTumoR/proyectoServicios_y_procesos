package com.proyecto_final.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@Table(name = "concesionarios")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Concesionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico del concecionario", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del concecionario", example = "MotoCenter Madrid")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Ubicasion del concecionario", example = "Calle Gran Via, 45, Madrid")
    private String ubicacion;
    
    @ManyToMany(mappedBy = "concesionarios")
    @Schema(description = "Lista de motos disponibles en el concecionario")
    private Set<Moto> motos;
}
