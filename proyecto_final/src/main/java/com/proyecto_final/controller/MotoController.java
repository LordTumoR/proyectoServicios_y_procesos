package com.proyecto_final.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto_final.dto.MotoDTO;
import com.proyecto_final.model.Concesionario;
import com.proyecto_final.model.Moto;
import com.proyecto_final.service.MotoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @Operation(summary = "Obtener todas las motos", description = "Devulve una lista con todas las motos registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Listado de motos obtenido con exito.")
    @GetMapping
    public List<MotoDTO> getAllMotos() {
        List<Moto> motos = motoService.getAllMotos();
        return motos.stream()
                    .map(this::entityToDto)  // Convertir cada moto a DTO
                    .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener una moto por ID", description = "Recupera los datos de una moto segun su identificador.")
    @ApiResponse(responseCode = "200", description = "Moto encontrada con exito.")
    @ApiResponse(responseCode = "404", description = "No se encontro ninguna moto con ese ID.")
    @GetMapping("/{id}")
    public ResponseEntity<MotoDTO> getMotoById(@PathVariable Long id) {
        Moto moto = motoService.getMotoById(id);
        return moto != null ? ResponseEntity.ok(entityToDto(moto)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear una moto", description = "Registra una nueva moto en el sistema.")
    @ApiResponse(responseCode = "201", description = "Moto creada correctamente.")
    @PostMapping
    public MotoDTO createMoto(@RequestBody MotoDTO motoDTO) {
        Moto moto = dtoToEntity(motoDTO);
        return entityToDto(motoService.createMoto(moto));
    }

    @Operation(summary = "Actualizar una moto", description = "Modifica los datos de una moto existente en el sistema.")
    @ApiResponse(responseCode = "200", description = "Moto actualizada con exito.")
    @ApiResponse(responseCode = "404", description = "No se encontro la moto que intenta actualizar.")
    @PutMapping("/{id}")
    public ResponseEntity<MotoDTO> updateMoto(@PathVariable Long id, @RequestBody MotoDTO motoDTO) {
        Moto moto = dtoToEntity(motoDTO);
        Moto updatedMoto = motoService.updateMoto(id, moto);
        return updatedMoto != null ? ResponseEntity.ok(entityToDto(updatedMoto)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar una moto", description = "Borra una moto del sistema segun su identificador.")
    @ApiResponse(responseCode = "204", description = "Moto eliminada con exito.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMoto(@PathVariable Long id) {
        motoService.deleteMoto(id);
        return ResponseEntity.noContent().build();
    }

    private MotoDTO entityToDto(Moto moto) {
        return new MotoDTO(moto.getId(), moto.getModelo(), moto.getMarca(), moto.getCilindrada(), moto.getCv(), moto.getPrecio());
    }
    

    private Moto dtoToEntity(MotoDTO motoDTO) {
    Set<Concesionario> concesionarios = new HashSet<>();  
    return new Moto(motoDTO.getId(), motoDTO.getModelo(), motoDTO.getMarca(),
                    motoDTO.getCilindrada(), motoDTO.getCv(), motoDTO.getPrecio(),
                    concesionarios, new HashSet<>());  
}

}
