package com.owt.boat_test.infrastructure.entities;

import jakarta.persistence.*;

/**
 * JPA Entity for representing boat data in the database.
 * Maps to the "boats" table in the database.
 */
@Entity
@Table(name = "boats")
public class BoatEntity {
  /**
   * Primary key identifier for the boat entity.
   * Auto-generated using the database's identity generation strategy.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The name of the boat.
   */
  private String name;
  
  /**
   * The description of the boat.
   */
  private String description;
  
  /**
   * Gets the unique identifier of the boat entity.
   * 
   * @return The boat entity's ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the boat entity.
   * 
   * @param id The boat entity's ID to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the boat entity.
   * 
   * @return The boat entity's name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the boat entity.
   * 
   * @param name The boat entity's name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the description of the boat entity.
   * 
   * @return The boat entity's description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the boat entity.
   * 
   * @param description The boat entity's description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }
}
