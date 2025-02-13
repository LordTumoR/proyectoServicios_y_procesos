package com.proyecto_final.controller;

import java.util.List;
import java.util.Optional;
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
import com.proyecto_final.dto.UserDTO;
import com.proyecto_final.model.Moto;
import com.proyecto_final.model.User;
import com.proyecto_final.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponse(responseCode = "201", description = "Usuario creado con éxito")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @Operation(summary = "Obtener un usuario por nombre de usuario", description = "Recupera los datos de un usuario según su nombre de usuario")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
    Optional<User> user = userService.getUserByUsername(username);
    if (user.isPresent()) {
        User usuario = user.get();
        // Creem una llista de motos amb els datos de les motos del usuari
        List<MotoDTO> motosDto = usuario.getMotos().stream()
                                       .map(moto -> new MotoDTO(moto.getId(), moto.getModelo(), moto.getMarca(), moto.getCilindrada(), moto.getCv(), moto.getPrecio()))
                                       .collect(Collectors.toList());
        // Creem el DTO del usuari amb les motos que te
        UserDTO userDTO = new UserDTO(usuario.getId(), usuario.getUsername(), usuario.getRole(), motosDto);
        
        // Tornem una resposta amb usuari i les motos
        return ResponseEntity.ok(userDTO);
    }
    // Si no esta el usuari torna error 
    return ResponseEntity.notFound().build();
}



    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario del sistema según su ID")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado con éxito")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar un usuario", description = "Modifica los datos de un usuario según su ID")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado con éxito")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Asignar una moto a un usuario", description = "Permite a un usuario comprar una moto según su ID y el ID de la moto")
    @ApiResponse(responseCode = "200", description = "Moto comprada con éxito")
    @ApiResponse(responseCode = "404", description = "Usuario o moto no encontrados")
    @PutMapping("/{userId}/comprar-moto/{motoId}")
    public ResponseEntity<UserDTO> comprarMoto(@PathVariable Long userId, @PathVariable Long motoId) {
        User usuarioActualizado = userService.comprarMoto(userId, motoId);
        UserDTO usuarioDTO = userToDto(usuarioActualizado);  
        return ResponseEntity.ok(usuarioDTO);
    }
    
    // esta funcio convertix el usuari en el dto
    private UserDTO userToDto(User user) {
        // les motos en dto
        List<MotoDTO> motosDTO = user.getMotos().stream()
                                    .map(this::entityToDto) // parsea cada moto en un dto 
                                    .collect(Collectors.toList());
        // fem el dto en les dades del user
        return new UserDTO(user.getId(), user.getUsername(), user.getRole(), motosDTO);
    }
    
    // aço convertix moto a dto
    private MotoDTO entityToDto(Moto moto) {
        // tornem el dto
        return new MotoDTO(moto.getId(), moto.getModelo(), moto.getMarca(), moto.getCilindrada(), moto.getCv(), moto.getPrecio());
    }


}
