package com.owt.boat_test.infrastructure.adapters.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.owt.boat_test.application.dtos.BoatDto;
import com.owt.boat_test.application.services.BoatService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/boats")
public class BoatController {

  private final BoatService boatService;
  
  public BoatController(BoatService boatService) {
    this.boatService = boatService;
  }

  @GetMapping
  public ResponseEntity<List<BoatDto>> getAllBoats() {
    List<BoatDto> boats = boatService.getAllBoats();
    return ResponseEntity.ok(boats);
  }

  @GetMapping("/{id}")
  public ResponseEntity<BoatDto> getBoatById(@PathVariable Long id) {
    BoatDto boat = boatService.getBoatById(id);
    return ResponseEntity.ok(boat);
  }

  @PostMapping
  public ResponseEntity<Object> createBoat(@Valid @RequestBody BoatDto boatDto) {
    BoatDto createdBoat = boatService.createBoat(boatDto);
    return new ResponseEntity<>(createdBoat, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateBoat(@PathVariable Long id, @Valid @RequestBody BoatDto boatDto) {
    BoatDto updatedBoat = boatService.updateBoat(id, boatDto);
    return ResponseEntity.ok(updatedBoat);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBoat(@PathVariable Long id) {
    boatService.deleteBoat(id);
    return ResponseEntity.noContent().build();
  }
}
