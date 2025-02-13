package com.proyecto_final.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la transferencia de datos del usuario")
public class UserDTO {

    @Schema(description = "ID del usuario", example = "2")
    private Long id;

    @Schema(description = "Nombre de usuario", example = "user2")
    private String username;

    @Schema(description = "Rol del usuario", example = "USER")
    private String role;

    @Schema(description = "Lista de motos asociadas al usuario")
    private List<MotoDTO> motos;

}
