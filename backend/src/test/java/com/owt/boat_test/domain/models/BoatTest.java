package com.owt.boat_test.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoatTest {

    @Test
    void testSetAndGetId() {
        // Arrange
        Boat boat = new Boat();
        Long id = 1L;
        
        // Act
        boat.setId(id);
        
        // Assert
        assertEquals(id, boat.getId());
    }
    
    @Test
    void testSetAndGetName() {
        // Arrange
        Boat boat = new Boat();
        String name = "Test Boat";
        
        // Act
        boat.setName(name);
        
        // Assert
        assertEquals(name, boat.getName());
    }
    
    @Test
    void testSetAndGetDescription() {
        // Arrange
        Boat boat = new Boat();
        String description = "Test description";
        
        // Act
        boat.setDescription(description);
        
        // Assert
        assertEquals(description, boat.getDescription());
    }
} 