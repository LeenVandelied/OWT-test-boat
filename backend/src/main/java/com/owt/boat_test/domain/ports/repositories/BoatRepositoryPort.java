package com.owt.boat_test.domain.ports.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.owt.boat_test.domain.models.Boat;

@Repository
public interface BoatRepositoryPort {
  Boat save(Boat boat);
  List<Boat> findAll();
  Boat findById(Long id);
  void delete(Long id);
}
