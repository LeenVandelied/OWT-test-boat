package com.owt.boat_test.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for Boat entities.
 * Used for transferring boat data between the application and presentation layers.
 * Contains validation constraints for input validation.
 */
public class BoatDto {
  private Long id;
  
  @NotBlank(message = "Name is required")
  @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
  private String name;
  
  @Size(max = 500, message = "Description cannot exceed 500 characters")
  private String description;
  
  /**
   * Default constructor required for serialization/deserialization.
   */
  public BoatDto() {
  }
  
  /**
   * Constructor with all fields for creating a complete BoatDto.
   * 
   * @param id The unique identifier of the boat
   * @param name The name of the boat
   * @param description The description of the boat
   */
  public BoatDto(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }
  
  /**
   * Gets the unique identifier of the boat.
   * 
   * @return The boat's ID
   */
  public Long getId() {
    return id;
  }
  
  /**
   * Sets the unique identifier of the boat.
   * 
   * @param id The boat's ID to set
   */
  public void setId(Long id) {
    this.id = id;
  }
  
  /**
   * Gets the name of the boat.
   * 
   * @return The boat's name
   */
  public String getName() {
    return name;
  }
  
  /**
   * Sets the name of the boat.
   * 
   * @param name The boat's name to set
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Gets the description of the boat.
   * 
   * @return The boat's description
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * Sets the description of the boat.
   * 
   * @param description The boat's description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }
}
