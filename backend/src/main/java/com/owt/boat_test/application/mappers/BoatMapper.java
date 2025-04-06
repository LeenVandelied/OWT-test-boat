package com.owt.boat_test.application.mappers;

import com.owt.boat_test.application.dtos.BoatDto;
import com.owt.boat_test.domain.models.Boat;

/**
 * Mapper class that handles conversions between Boat domain models and BoatDto objects.
 * Provides utility methods to transform objects between the domain and application layers.
 */
public class BoatMapper {
  
  /**
   * Converts a Boat domain model to a BoatDto.
   * 
   * @param boat The Boat domain model to convert
   * @return A new BoatDto containing the same data as the domain model, or null if input is null
   */
  public static BoatDto toDto(Boat boat) {
    if (boat == null) return null;
    return new BoatDto(
      boat.getId(),
      boat.getName(),
      boat.getDescription()
    );
  }
  
  /**
   * Converts a BoatDto to a Boat domain model.
   * 
   * @param dto The BoatDto to convert
   * @return A new Boat domain model containing the same data as the DTO, or null if input is null
   */
  public static Boat toEntity(BoatDto dto) {
    if (dto == null) return null;
    Boat boat = new Boat();
    boat.setId(dto.getId());
    boat.setName(dto.getName());
    boat.setDescription(dto.getDescription());
    return boat;
  }
}
