package com.owt.boat_test.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.owt.boat_test.application.dtos.BoatDto;
import com.owt.boat_test.domain.models.Boat;
import com.owt.boat_test.domain.ports.repositories.BoatRepositoryPort;

@ExtendWith(MockitoExtension.class)
class BoatServiceTest {

    @Mock
    private BoatRepositoryPort boatRepository;

    @InjectMocks
    private BoatService boatService;

    private Boat testBoat;
    private BoatDto testBoatDto;

    @BeforeEach
    void setUp() {
        testBoat = new Boat();
        testBoat.setId(1L);
        testBoat.setName("Test Boat");
        testBoat.setDescription("A boat for testing");

        testBoatDto = new BoatDto();
        testBoatDto.setId(1L);
        testBoatDto.setName("Test Boat");
        testBoatDto.setDescription("A boat for testing");
    }

    @Test
    void getAllBoats_ShouldReturnListOfBoatDtos() {
        List<Boat> boats = Arrays.asList(testBoat);
        when(boatRepository.findAll()).thenReturn(boats);

        List<BoatDto> result = boatService.getAllBoats();

        assertEquals(1, result.size());
        assertEquals(testBoatDto.getId(), result.get(0).getId());
        assertEquals(testBoatDto.getName(), result.get(0).getName());
        assertEquals(testBoatDto.getDescription(), result.get(0).getDescription());
        verify(boatRepository).findAll();
    }

    @Test
    void getBoatById_WithExistingId_ShouldReturnBoatDto() {
        when(boatRepository.findById(1L)).thenReturn(testBoat);

        BoatDto result = boatService.getBoatById(1L);

        assertNotNull(result);
        assertEquals(testBoatDto.getId(), result.getId());
        assertEquals(testBoatDto.getName(), result.getName());
        assertEquals(testBoatDto.getDescription(), result.getDescription());
        verify(boatRepository).findById(1L);
    }

    @Test
    void getBoatById_WithNonExistingId_ShouldThrowException() {
        when(boatRepository.findById(999L)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> {
            boatService.getBoatById(999L);
        });
        verify(boatRepository).findById(999L);
    }

    @Test
    void createBoat_ShouldReturnSavedBoatDto() {
        Boat boatToSave = new Boat();
        boatToSave.setName("Test Boat");
        boatToSave.setDescription("A boat for testing");

        BoatDto dtoToSave = new BoatDto();
        dtoToSave.setName("Test Boat");
        dtoToSave.setDescription("A boat for testing");

        when(boatRepository.save(any(Boat.class))).thenReturn(testBoat);

        BoatDto result = boatService.createBoat(dtoToSave);

        assertNotNull(result);
        assertEquals(testBoatDto.getId(), result.getId());
        assertEquals(testBoatDto.getName(), result.getName());
        assertEquals(testBoatDto.getDescription(), result.getDescription());
        verify(boatRepository).save(any(Boat.class));
    }

    @Test
    void updateBoat_WithExistingId_ShouldReturnUpdatedBoatDto() {
        when(boatRepository.findById(1L)).thenReturn(testBoat);
        
        BoatDto dtoToUpdate = new BoatDto();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setName("Updated Boat");
        dtoToUpdate.setDescription("An updated description");

        Boat updatedBoat = new Boat();
        updatedBoat.setId(1L);
        updatedBoat.setName("Updated Boat");
        updatedBoat.setDescription("An updated description");

        when(boatRepository.save(any(Boat.class))).thenReturn(updatedBoat);

        BoatDto result = boatService.updateBoat(1L, dtoToUpdate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Boat", result.getName());
        assertEquals("An updated description", result.getDescription());
        verify(boatRepository).findById(1L);
        verify(boatRepository).save(any(Boat.class));
    }

    @Test
    void updateBoat_WithNonExistingId_ShouldThrowException() {
        when(boatRepository.findById(999L)).thenReturn(null);
        
        BoatDto dtoToUpdate = new BoatDto();
        dtoToUpdate.setId(999L);
        dtoToUpdate.setName("Updated Boat");
        dtoToUpdate.setDescription("An updated description");

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            boatService.updateBoat(999L, dtoToUpdate);
        });
        verify(boatRepository).findById(999L);
        verify(boatRepository, never()).save(any(Boat.class));
    }

    @Test
    void deleteBoat_WithExistingId_ShouldCallRepositoryDelete() {
        // Arrange
        when(boatRepository.findById(1L)).thenReturn(testBoat);

        // Act
        boatService.deleteBoat(1L);

        // Assert
        verify(boatRepository).findById(1L);
        verify(boatRepository).delete(1L);
    }

    @Test
    void deleteBoat_WithNonExistingId_ShouldThrowException() {
        // Arrange
        when(boatRepository.findById(999L)).thenReturn(null);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            boatService.deleteBoat(999L);
        });
        verify(boatRepository).findById(999L);
        verify(boatRepository, never()).delete(anyLong());
    }
} 