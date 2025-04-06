package com.owt.boat_test.infrastructure.adapters.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.owt.boat_test.domain.models.Boat;
import com.owt.boat_test.infrastructure.entities.BoatEntity;

@ExtendWith(MockitoExtension.class)
class BoatRepositoryAdapterTest {

    @Mock
    private BoatJpaRepository boatJpaRepository;

    @InjectMocks
    private BoatRepositoryAdapter boatRepositoryAdapter;

    private Boat testBoat;
    private BoatEntity testBoatEntity;

    @BeforeEach
    void setUp() {
        // Initialiser un bateau de test (domaine)
        testBoat = new Boat();
        testBoat.setId(1L);
        testBoat.setName("Test Boat");
        testBoat.setDescription("A boat for testing");

        // Initialiser une entité de bateau correspondante
        testBoatEntity = new BoatEntity();
        testBoatEntity.setId(1L);
        testBoatEntity.setName("Test Boat");
        testBoatEntity.setDescription("A boat for testing");
    }

    @Test
    void save_ShouldReturnSavedBoat() {
        // Arrange
        when(boatJpaRepository.save(any(BoatEntity.class))).thenReturn(testBoatEntity);

        // Act
        Boat result = boatRepositoryAdapter.save(testBoat);

        // Assert
        assertNotNull(result);
        assertEquals(testBoat.getId(), result.getId());
        assertEquals(testBoat.getName(), result.getName());
        assertEquals(testBoat.getDescription(), result.getDescription());
        verify(boatJpaRepository).save(any(BoatEntity.class));
    }

    @Test
    void findAll_ShouldReturnAllBoats() {
        // Arrange
        List<BoatEntity> boatEntities = Arrays.asList(testBoatEntity);
        when(boatJpaRepository.findAll()).thenReturn(boatEntities);

        // Act
        List<Boat> result = boatRepositoryAdapter.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals(testBoat.getId(), result.get(0).getId());
        assertEquals(testBoat.getName(), result.get(0).getName());
        assertEquals(testBoat.getDescription(), result.get(0).getDescription());
        verify(boatJpaRepository).findAll();
    }

    @Test
    void findById_WithExistingId_ShouldReturnBoat() {
        // Arrange
        when(boatJpaRepository.findById(1L)).thenReturn(Optional.of(testBoatEntity));

        // Act
        Boat result = boatRepositoryAdapter.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testBoat.getId(), result.getId());
        assertEquals(testBoat.getName(), result.getName());
        assertEquals(testBoat.getDescription(), result.getDescription());
        verify(boatJpaRepository).findById(1L);
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnNull() {
        // Arrange
        when(boatJpaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Boat result = boatRepositoryAdapter.findById(999L);

        // Assert
        assertNull(result);
        verify(boatJpaRepository).findById(999L);
    }

    @Test
    void delete_ShouldCallJpaRepositoryDeleteById() {
        // Arrange
        doNothing().when(boatJpaRepository).deleteById(1L);

        // Act
        boatRepositoryAdapter.delete(1L);

        // Assert
        verify(boatJpaRepository).deleteById(1L);
    }
} 