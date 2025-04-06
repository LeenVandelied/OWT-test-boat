package com.owt.boat_test.infrastructure.mappers;

import com.owt.boat_test.domain.models.Boat;
import com.owt.boat_test.infrastructure.entities.BoatEntity;

/**
 * Mapper class for converting between domain model Boat objects and infrastructure layer BoatEntity objects.
 * Provides methods to transform objects between the domain and infrastructure layers.
 */
public class BoatEntityMapper {
  
  /**
   * Converts a Boat domain model to a BoatEntity for persistence.
   * 
   * @param boat The Boat domain model to convert
   * @return A new BoatEntity containing the same data as the domain model, or null if input is null
   */
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

  /**
   * Converts a BoatEntity from the database to a Boat domain model.
   * 
   * @param entity The BoatEntity to convert
   * @return A new Boat domain model containing the same data as the entity, or null if input is null
   */
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