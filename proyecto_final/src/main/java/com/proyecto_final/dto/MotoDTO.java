package com.proyecto_final.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para la moto, usado para la transferencia de datos")
public class MotoDTO {

    @Schema(description = "ID de la moto", example = "1")
    private Long id;

    @Schema(description = "Modelo de la moto", example = "Cota 311")
    private String modelo;

    @Schema(description = "Marca de la moto", example = "Montesa")
    private String marca;

    @Schema(description = "Cilindrada de la moto en cc", example = "125")
    private String cilindrada;

    @Schema(description = "Número de caballos de fuerza de la moto", example = "30")
    private int cv;

    @Schema(description = "Precio de la moto en euros", example = "5000")
    private int precio;

    public MotoDTO(Long id, String modelo, String marca, String cilindrada, int cv, int precio) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.cilindrada = cilindrada;
        this.cv = cv;
        this.precio = precio;
    }
}
