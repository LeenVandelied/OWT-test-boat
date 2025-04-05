package com.owt.boat_test.application.mappers;

import com.owt.boat_test.application.dtos.BoatDto;
import com.owt.boat_test.domain.models.Boat;

public class BoatMapper {
  
  public static BoatDto toDto(Boat boat) {
    if (boat == null) return null;
    return new BoatDto(
      boat.getId(),
      boat.getName(),
      boat.getDescription()
    );
  }
  
  public static Boat toEntity(BoatDto dto) {
    if (dto == null) return null;
    Boat boat = new Boat();
    boat.setId(dto.getId());
    boat.setName(dto.getName());
    boat.setDescription(dto.getDescription());
    return boat;
  }
}
