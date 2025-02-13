package com.proyecto_final.dto;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para el concesionario, usado para la transferencia de datos")
public class ConcesionarioDTO {

    @Schema(description = "ID del concesionario", example = "1")
    private Long id;

    @Schema(description = "Nombre del concesionario", example = "MotoCenter")
    private String nombre;

    @Schema(description = "Ubicación del concesionario", example = "Madrid, España")
    private String ubicacion;

    @Schema(description = "Lista de motos asociadas a este concesionario")
    private Set<MotoDTO> motos = new HashSet<>(); 

    public ConcesionarioDTO(Long id, String nombre, String ubicacion, Set<MotoDTO> motos) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.motos = motos;
    }
}
