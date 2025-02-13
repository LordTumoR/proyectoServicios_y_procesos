package com.proyecto_final.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto_final.model.Moto;
import com.proyecto_final.repository.MotoRepository;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> getAllMotos() {
        return motoRepository.findAll();
    }

    public Moto getMotoById(Long id) {
        return motoRepository.findById(id).orElse(null);
    }

    public Moto createMoto(Moto moto) {
        return motoRepository.save(moto);
    }

    public Moto updateMoto(Long id, Moto moto) {
        return motoRepository.findById(id)
                .map(existingMoto -> {
                    existingMoto.setMarca(moto.getMarca());
                    existingMoto.setModelo(moto.getModelo());
                    existingMoto.setPrecio(moto.getPrecio());
                    return motoRepository.save(existingMoto);
                })
                .orElse(null);
    }

    public void deleteMoto(Long id) {
        motoRepository.deleteById(id);
    }
}
