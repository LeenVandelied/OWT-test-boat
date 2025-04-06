package com.owt.boat_test.infrastructure.adapters.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.owt.boat_test.domain.models.Boat;
import com.owt.boat_test.domain.ports.repositories.BoatRepositoryPort;
import com.owt.boat_test.infrastructure.entities.BoatEntity;
import com.owt.boat_test.infrastructure.mappers.BoatEntityMapper;

/**
 * Adapter implementation of the BoatRepositoryPort interface.
 * This class bridges the domain layer with the JPA infrastructure for boat persistence.
 * It translates between domain model objects and JPA entities.
 */
@Component
public class BoatRepositoryAdapter implements BoatRepositoryPort {
  
  private final BoatJpaRepository boatJpaRepository;

  /**
   * Constructor for BoatRepositoryAdapter with dependency injection.
   * 
   * @param boatJpaRepository The Spring Data JPA repository for boats
   */
  public BoatRepositoryAdapter(BoatJpaRepository boatJpaRepository) {
    this.boatJpaRepository = boatJpaRepository;
  }

  /**
   * {@inheritDoc}
   * Converts the domain model to an entity, saves it, and converts back to domain model.
   */
  @Override
  public Boat save(Boat boat) {
    BoatEntity boatEntity = BoatEntityMapper.toEntity(boat);
    BoatEntity savedBoatEntity = boatJpaRepository.save(boatEntity);
    return BoatEntityMapper.toDomain(savedBoatEntity);
  }
  
  /**
   * {@inheritDoc}
   * Retrieves all boat entities and converts them to domain models.
   */
  @Override
  public List<Boat> findAll() {
    return boatJpaRepository.findAll().stream()
        .map(BoatEntityMapper::toDomain)
        .collect(Collectors.toList());
  }
  
  /**
   * {@inheritDoc}
   * Retrieves a boat entity by ID and converts it to domain model if found.
   */
  @Override
  public Boat findById(Long id) {
    return boatJpaRepository.findById(id)
        .map(BoatEntityMapper::toDomain)
        .orElse(null);
  }
  
  /**
   * {@inheritDoc}
   * Deletes a boat entity by its ID.
   */
  @Override
  public void delete(Long id) {
    boatJpaRepository.deleteById(id);
  }
}