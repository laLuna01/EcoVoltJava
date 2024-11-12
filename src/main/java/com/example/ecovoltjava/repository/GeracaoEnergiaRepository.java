package com.example.ecovoltjava.repository;

import com.example.ecovoltjava.model.GeracaoEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeracaoEnergiaRepository extends JpaRepository<GeracaoEnergia, Long> {
}
