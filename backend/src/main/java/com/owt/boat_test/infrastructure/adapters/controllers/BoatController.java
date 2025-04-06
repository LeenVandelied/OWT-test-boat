package com.owt.boat_test.infrastructure.adapters.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.owt.boat_test.application.dtos.BoatDto;
import com.owt.boat_test.application.services.BoatService;

import jakarta.validation.Valid;

/**
 * REST Controller responsible for handling boat-related HTTP requests.
 * Provides endpoints for CRUD operations on boat resources.
 */
@RestController
@RequestMapping("/boats")
public class BoatController {

  private final BoatService boatService;
  
  /**
   * Constructor for BoatController with dependency injection.
   * 
   * @param boatService The service handling business logic for boat operations
   */
  public BoatController(BoatService boatService) {
    this.boatService = boatService;
  }

  /**
   * Retrieves all boats from the system.
   * 
   * @return ResponseEntity containing a list of all boats
   */
  @GetMapping
  public ResponseEntity<List<BoatDto>> getAllBoats() {
    List<BoatDto> boats = boatService.getAllBoats();
    return ResponseEntity.ok(boats);
  }

  /**
   * Retrieves a specific boat by its ID.
   * 
   * @param id The ID of the boat to retrieve
   * @return ResponseEntity containing the requested boat
   */
  @GetMapping("/{id}")
  public ResponseEntity<BoatDto> getBoatById(@PathVariable Long id) {
    BoatDto boat = boatService.getBoatById(id);
    return ResponseEntity.ok(boat);
  }

  /**
   * Creates a new boat in the system.
   * 
   * @param boatDto The boat data to create
   * @return ResponseEntity containing the created boat with HTTP status 201 (Created)
   */
  @PostMapping
  public ResponseEntity<Object> createBoat(@Valid @RequestBody BoatDto boatDto) {
    BoatDto createdBoat = boatService.createBoat(boatDto);
    return new ResponseEntity<>(createdBoat, HttpStatus.CREATED);
  }

  /**
   * Updates an existing boat in the system.
   * 
   * @param id The ID of the boat to update
   * @param boatDto The updated boat data
   * @return ResponseEntity containing the updated boat
   */
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateBoat(@PathVariable Long id, @Valid @RequestBody BoatDto boatDto) {
    BoatDto updatedBoat = boatService.updateBoat(id, boatDto);
    return ResponseEntity.ok(updatedBoat);
  }

  /**
   * Deletes a boat from the system.
   * 
   * @param id The ID of the boat to delete
   * @return ResponseEntity with HTTP status 204 (No Content)
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBoat(@PathVariable Long id) {
    boatService.deleteBoat(id);
    return ResponseEntity.noContent().build();
  }
}
