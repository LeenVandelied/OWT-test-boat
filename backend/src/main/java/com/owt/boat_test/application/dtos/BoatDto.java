package com.owt.boat_test.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BoatDto {
  private Long id;
  
  @NotBlank(message = "Name is required")
  @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
  private String name;
  
  @Size(max = 500, message = "Description cannot exceed 500 characters")
  private String description;
  
  public BoatDto() {
  }
  
  public BoatDto(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
}
