package com.proyecto_final.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto_final.model.Concesionario;
import com.proyecto_final.model.Moto;
import com.proyecto_final.repository.ConcesionarioRepository;
import com.proyecto_final.repository.MotoRepository;

@Service
public class ConcesionarioService {

    @Autowired
    private ConcesionarioRepository concesionarioRepository;
    @Autowired
    private MotoRepository motoRepository;

    public Concesionario createConcesionario(Concesionario concesionario) {
        return concesionarioRepository.save(concesionario);
    }

    public Optional<Concesionario> getConcesionarioById(Long id) {
        return concesionarioRepository.findById(id);
    }

    public List<Concesionario> getAllConcesionarios() {
        return concesionarioRepository.findAll();
    }

    public Concesionario updateConcesionario(Long id, Concesionario concesionario) {
        if (concesionarioRepository.existsById(id)) {
            concesionario.setId(id);
            return concesionarioRepository.save(concesionario);
        }
        return null;
    }

    public void deleteConcesionario(Long id) {
        concesionarioRepository.deleteById(id);
    }
    public Concesionario agregarMoto(Long concesionarioId, Long motoId) {
        Concesionario concesionario = concesionarioRepository.findById(concesionarioId)
            .orElseThrow(() -> new RuntimeException("Concesionario no encontrado"));
    
        Moto moto = motoRepository.findById(motoId)
            .orElseThrow(() -> new RuntimeException("Moto no encontrada"));
    
        concesionario.getMotos().add(moto);
        moto.getConcesionarios().add(concesionario); 
    
        motoRepository.save(moto); 
        return concesionarioRepository.save(concesionario); 
    }
}


