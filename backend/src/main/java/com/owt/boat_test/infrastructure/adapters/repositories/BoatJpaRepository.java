package com.owt.boat_test.infrastructure.adapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.owt.boat_test.infrastructure.entities.BoatEntity;

@Repository
public interface BoatJpaRepository extends JpaRepository<BoatEntity, Long> { 
}
