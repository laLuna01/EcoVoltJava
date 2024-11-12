package com.example.ecovoltjava.repository;

import com.example.ecovoltjava.model.Consumidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumidorRepository extends JpaRepository<Consumidor, Long> {
}
