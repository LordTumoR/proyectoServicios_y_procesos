package com.proyecto_final.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto_final.model.Moto;
import com.proyecto_final.model.User;
import com.proyecto_final.repository.MotoRepository;
import com.proyecto_final.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MotoRepository motoRepository;

    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    
    public User updateUser(Long userId, User user) {
        if (userRepository.existsById(userId)) {
            User existingUser = userRepository.findById(userId).get();
            
            if (user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(user.getPassword());
            }
            if (user.getRole() != null) {
                existingUser.setRole(user.getRole());
            }
    
            return userRepository.save(existingUser);
        }
        return null;
    }
    public User comprarMoto(Long userId, Long motoId) {
        User usuario = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
        Moto moto = motoRepository.findById(motoId)
            .orElseThrow(() -> new RuntimeException("Moto no encontrada"));
    
        usuario.getMotos().add(moto);
        moto.getUsuarios().add(usuario);
    
        motoRepository.save(moto); 
        return userRepository.save(usuario); 
    }
    

}
