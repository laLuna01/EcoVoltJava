package com.example.ecovoltjava.repository;

import com.example.ecovoltjava.model.TipoFonte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoFonteRepository extends JpaRepository<TipoFonte, Long> {
}
