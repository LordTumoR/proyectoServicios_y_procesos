package com.proyecto_final.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto_final.model.Moto;

public interface MotoRepository extends JpaRepository<Moto, Long> {
}
