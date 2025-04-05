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

@Service
public class BoatService {
  
  private final BoatRepositoryPort boatRepository;

  public BoatService(BoatRepositoryPort boatRepository) {
    this.boatRepository = boatRepository;
  }

  public List<BoatDto> getAllBoats() {
    List<Boat> boats = boatRepository.findAll();
    return boats.stream()
      .map(BoatMapper::toDto)
      .collect(Collectors.toList());
  }

  public BoatDto getBoatById(Long id) {
    Boat boat = boatRepository.findById(id);
    if (boat == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boat not found with id: " + id);
    }
    return BoatMapper.toDto(boat);
  }

  public BoatDto createBoat(BoatDto boatDto) {
    Boat boat = BoatMapper.toEntity(boatDto);
    Boat savedBoat = boatRepository.save(boat);
    return BoatMapper.toDto(savedBoat);
  }
  
  public BoatDto updateBoat(Long id, BoatDto boatDto) {
    Boat existingBoat = boatRepository.findById(id);
    if (existingBoat == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boat not found with id: " + id);
    }
    
    Boat boat = BoatMapper.toEntity(boatDto);
    Boat updatedBoat = boatRepository.save(boat);
    return BoatMapper.toDto(updatedBoat);
  }
  
  public void deleteBoat(Long id) {
    Boat existingBoat = boatRepository.findById(id);
    if (existingBoat == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Boat not found with id: " + id);
    }
    
    boatRepository.delete(id);
  }
  
}
