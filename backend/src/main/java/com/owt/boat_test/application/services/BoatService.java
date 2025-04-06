package com.owt.boat_test.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.owt.boat_test.application.dtos.BoatDto;
import com.owt.boat_test.application.mappers.BoatMapper;
import com.owt.boat_test.domain.models.Boat;
import com.owt.boat_test.domain.ports.repositories.BoatRepositoryPort;

/**
 * Service class that implements boat-related business logic.
 * Acts as an intermediary between controllers and repositories.
 */
@Service
public class BoatService {
  
  private final BoatRepositoryPort boatRepository;

  /**
   * Constructor for BoatService with dependency injection.
   * 
   * @param boatRepository The repository port for boat persistence operations
   */
  public BoatService(BoatRepositoryPort boatRepository) {
    this.boatRepository = boatRepository;
  }

  /**
   * Retrieves all boats from the repository.
   * 
   * @return A list of all boats converted to DTOs
   */
  public List<BoatDto> getAllBoats() {
    List<Boat> boats = boatRepository.findAll();
    return boats.stream()
      .map(BoatMapper::toDto)
      .collect(Collectors.toList());
  }

  /**
   * Retrieves a specific boat by its ID.
   * 
   * @param id The ID of the boat to retrieve
   * @return The found boat converted to DTO
   * @throws ResponseStatusException with HTTP 404 if the boat is not found
   */
  public BoatDto getBoatById(Long id) {
    Boat boat = boatRepository.findById(id);
    if (boat == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boat not found with id: " + id);
    }
    return BoatMapper.toDto(boat);
  }

  /**
   * Creates a new boat in the system.
   * 
   * @param boatDto The boat data to create
   * @return The created boat converted to DTO
   */
  public BoatDto createBoat(BoatDto boatDto) {
    Boat boat = BoatMapper.toEntity(boatDto);
    Boat savedBoat = boatRepository.save(boat);
    return BoatMapper.toDto(savedBoat);
  }
  
  /**
   * Updates an existing boat in the system.
   * 
   * @param id The ID of the boat to update
   * @param boatDto The updated boat data
   * @return The updated boat converted to DTO
   * @throws ResponseStatusException with HTTP 404 if the boat is not found
   */
  public BoatDto updateBoat(Long id, BoatDto boatDto) {
    Boat existingBoat = boatRepository.findById(id);
    if (existingBoat == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boat not found with id: " + id);
    }
    
    Boat boat = BoatMapper.toEntity(boatDto);
    Boat updatedBoat = boatRepository.save(boat);
    return BoatMapper.toDto(updatedBoat);
  }
  
  /**
   * Deletes a boat from the system.
   * 
   * @param id The ID of the boat to delete
   * @throws ResponseStatusException with HTTP 404 if the boat is not found
   */
  public void deleteBoat(Long id) {
    Boat existingBoat = boatRepository.findById(id);
    if (existingBoat == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boat not found with id: " + id);
    }
    
    boatRepository.delete(id);
  }
  
}
