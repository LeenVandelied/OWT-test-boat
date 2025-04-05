package com.owt.boat_test.infrastructure.adapters.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.owt.boat_test.application.dtos.BoatDto;
import com.owt.boat_test.application.services.BoatService;

@RestController
@RequestMapping("/boats")
public class BoatController {

  private final BoatService boatService;
  
  public BoatController(BoatService boatService) {
    this.boatService = boatService;
  }

  @GetMapping
  public List<BoatDto> getAllBoats() {
    return boatService.getAllBoats();
  }

  @GetMapping("/{id}")
  public BoatDto getBoatById(@PathVariable Long id) {
    return boatService.getBoatById(id);
  }

  @PostMapping
  public BoatDto createBoat(@RequestBody BoatDto boatDto) {
    return boatService.createBoat(boatDto);
  }

  @PutMapping("/{id}")
  public BoatDto updateBoat(@PathVariable Long id, @RequestBody BoatDto boatDto) {
    return boatService.updateBoat(id, boatDto);
  }

  @DeleteMapping("/{id}")
  public void deleteBoat(@PathVariable Long id) {
    boatService.deleteBoat(id);
  }
}
