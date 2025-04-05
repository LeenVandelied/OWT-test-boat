package com.owt.boat_test.infrastructure.mappers;

import com.owt.boat_test.domain.models.Boat;
import com.owt.boat_test.infrastructure.entities.BoatEntity;

public class BoatEntityMapper {
  
  public static BoatEntity toEntity(Boat boat) {
    if (boat == null) {
      return null;
    }
    BoatEntity entity = new BoatEntity();
    entity.setId(boat.getId());
    entity.setName(boat.getName());
    entity.setDescription(boat.getDescription());
    return entity;
  }

  public static Boat toDomain(BoatEntity entity) {
    if (entity == null) {
      return null;
    }
    Boat boat = new Boat();
    boat.setId(entity.getId());
    boat.setName(entity.getName());
    boat.setDescription(entity.getDescription());
    return boat;
  }
} 