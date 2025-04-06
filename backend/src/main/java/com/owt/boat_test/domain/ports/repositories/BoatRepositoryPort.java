package com.owt.boat_test.domain.ports.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.owt.boat_test.domain.models.Boat;

/**
 * Port interface for boat repository operations.
 * This interface defines the contract for boat data persistence operations,
 * following the Hexagonal Architecture pattern.
 */
@Repository
public interface BoatRepositoryPort {
  
  /**
   * Saves a boat to the persistence store.
   * 
   * @param boat The boat to save
   * @return The saved boat, potentially with ID updated
   */
  Boat save(Boat boat);
  
  /**
   * Retrieves all boats from the persistence store.
   * 
   * @return A list of all boats
   */
  List<Boat> findAll();
  
  /**
   * Finds a boat by its ID.
   * 
   * @param id The ID of the boat to find
   * @return The found boat or null if not found
   */
  Boat findById(Long id);
  
  /**
   * Deletes a boat by its ID from the persistence store.
   * 
   * @param id The ID of the boat to delete
   */
  void delete(Long id);
}
