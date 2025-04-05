package com.owt.boat_test.infrastructure.adapters.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.owt.boat_test.domain.models.Boat;
import com.owt.boat_test.domain.ports.repositories.BoatRepositoryPort;
import com.owt.boat_test.infrastructure.entities.BoatEntity;
import com.owt.boat_test.infrastructure.mappers.BoatEntityMapper;

@Component
public class BoatRepositoryAdapter implements BoatRepositoryPort {
  
  private final BoatJpaRepository boatJpaRepository;

  public BoatRepositoryAdapter(BoatJpaRepository boatJpaRepository) {
    this.boatJpaRepository = boatJpaRepository;
  }

  @Override
  public Boat save(Boat boat) {
    BoatEntity boatEntity = BoatEntityMapper.toEntity(boat);
    BoatEntity savedBoatEntity = boatJpaRepository.save(boatEntity);
    return BoatEntityMapper.toDomain(savedBoatEntity);
  }
  
  @Override
  public List<Boat> findAll() {
    return boatJpaRepository.findAll().stream()
        .map(BoatEntityMapper::toDomain)
        .collect(Collectors.toList());
  }
  
  @Override
  public Boat findById(Long id) {
    return boatJpaRepository.findById(id)
        .map(BoatEntityMapper::toDomain)
        .orElse(null);
  }
  
  @Override
  public void delete(Long id) {
    boatJpaRepository.deleteById(id);
  }
}