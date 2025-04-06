package com.owt.boat_test.domain.models;

/**
 * Domain model class representing a Boat entity.
 * This class is part of the domain layer and contains the core business data.
 */
public class Boat {
  private Long id;
  private String name;
  private String description;
  
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
