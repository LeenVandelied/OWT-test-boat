package com.owt.boat_test.infrastructure.adapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.owt.boat_test.infrastructure.entities.BoatEntity;

/**
 * Spring Data JPA repository interface for boat entities.
 * Provides standard CRUD operations and database access for the BoatEntity class.
 * Extends JpaRepository to inherit methods like findAll, findById, save, deleteById, etc.
 */
@Repository
public interface BoatJpaRepository extends JpaRepository<BoatEntity, Long> { 
}
