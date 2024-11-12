package com.example.ecovoltjava.repository;

import com.example.ecovoltjava.model.TipoConsumidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoConsumidorRepository extends JpaRepository<TipoConsumidor, Long> {
}
