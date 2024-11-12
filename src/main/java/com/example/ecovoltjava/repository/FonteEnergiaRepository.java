package com.example.ecovoltjava.repository;

import com.example.ecovoltjava.model.FonteEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FonteEnergiaRepository extends JpaRepository<FonteEnergia, Long> {
}
