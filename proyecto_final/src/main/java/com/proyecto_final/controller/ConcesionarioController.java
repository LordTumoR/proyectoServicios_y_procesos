package com.proyecto_final.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

import com.proyecto_final.dto.ConcesionarioDTO;
import com.proyecto_final.dto.MotoDTO;
import com.proyecto_final.model.Concesionario;
import com.proyecto_final.model.Moto;
import com.proyecto_final.service.ConcesionarioService;
import com.proyecto_final.service.MotoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/concesionarios")
public class ConcesionarioController {

    @Autowired
    private ConcesionarioService concesionarioService;
    @Autowired
    private MotoService motoService;

    @Operation(summary = "Crear un concesionario", description = "Se registra un nuevo concesionario en el sistema.")
    @ApiResponse(responseCode = "200", description = "Concesionario registrado correctamente.")
    @PostMapping
    public ResponseEntity<ConcesionarioDTO> createConcesionario(@RequestBody ConcesionarioDTO concesionarioDTO) {
        Concesionario concesionario = dtoToEntity(concesionarioDTO);
        Concesionario created = concesionarioService.createConcesionario(concesionario);
        return ResponseEntity.ok(entityToDto(created));
    }

    @Operation(summary = "Obtener un concesionario por ID", description = "Busca un concesionario por su ID.")
    @ApiResponse(responseCode = "200", description = "Concesionario encontrado.")
    @ApiResponse(responseCode = "404", description = "No se encontró el concesionario.")
    @GetMapping("/{id}")
    public ResponseEntity<ConcesionarioDTO> getConcesionarioById(@PathVariable Long id) {
        Optional<Concesionario> concesionario = concesionarioService.getConcesionarioById(id);
        return concesionario.map(c -> ResponseEntity.ok(entityToDto(c)))
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener todos los concesionarios", description = "Lista todos los concesionarios registrados.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido con éxito.")
    @GetMapping
    public ResponseEntity<List<ConcesionarioDTO>> getAllConcesionarios() {
        List<Concesionario> concesionarios = concesionarioService.getAllConcesionarios();
        List<ConcesionarioDTO> dtoList = concesionarios.stream()
                                                       .map(this::entityToDto)
                                                       .toList();
        return ResponseEntity.ok(dtoList);
    }

    @Operation(summary = "Actualizar un concesionario", description = "Modifica los datos de un concesionario existente.")
    @ApiResponse(responseCode = "200", description = "Concesionario actualizado.")
    @ApiResponse(responseCode = "404", description = "Concesionario no encontrado.")
    @PutMapping("/{id}")
    public ResponseEntity<ConcesionarioDTO> updateConcesionario(@PathVariable Long id, @RequestBody ConcesionarioDTO concesionarioDTO) {
        Concesionario concesionario = dtoToEntity(concesionarioDTO);
        Concesionario updated = concesionarioService.updateConcesionario(id, concesionario);
        return updated != null ? ResponseEntity.ok(entityToDto(updated)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar un concesionario", description = "Elimina un concesionario por su ID.")
    @ApiResponse(responseCode = "204", description = "Concesionario eliminado.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcesionario(@PathVariable Long id) {
        concesionarioService.deleteConcesionario(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Agregar una moto a un concesionario", description = "Asocia una moto existente a un concesionario específico.")
    @ApiResponse(responseCode = "200", description = "Moto agregada al concesionario con éxito.")
    @ApiResponse(responseCode = "404", description = "No se encontró el concesionario o la moto especificada.")
    @PutMapping("/{concesionarioId}/agregar-moto/{motoId}")
    public ResponseEntity<ConcesionarioDTO> agregarMoto(@PathVariable Long concesionarioId, @PathVariable Long motoId) {
        Concesionario concesionarioActualizado = concesionarioService.agregarMoto(concesionarioId, motoId);
        ConcesionarioDTO concesionarioDTO = entityToDto(concesionarioActualizado);
            return ResponseEntity.ok(concesionarioDTO);
}

private ConcesionarioDTO entityToDto(Concesionario concesionario) {
    Set<MotoDTO> motosDto = concesionario.getMotos().stream()
                                         .map(moto -> new MotoDTO(
                                             moto.getId(),
                                             moto.getModelo(),
                                             moto.getMarca(),
                                             moto.getCilindrada(),
                                             moto.getCv(),
                                             moto.getPrecio()))
                                         .collect(Collectors.toSet());

    return new ConcesionarioDTO(concesionario.getId(), concesionario.getNombre(), concesionario.getUbicacion(), motosDto);
}



private Concesionario dtoToEntity(ConcesionarioDTO concesionarioDTO) {
    Set<Moto> motos = new HashSet<>();
    for (MotoDTO motoDTO : concesionarioDTO.getMotos()) {
        Moto moto = motoService.getMotoById(motoDTO.getId());  
        if (moto != null) {
            motos.add(moto);
        }
    }
    return new Concesionario(concesionarioDTO.getId(), concesionarioDTO.getNombre(), concesionarioDTO.getUbicacion(), motos);
}

}
