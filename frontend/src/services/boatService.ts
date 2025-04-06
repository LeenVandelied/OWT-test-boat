import { Boat } from '@/types/boat';
import api from './axiosConfig';
import { handleApiError } from './errorHandler';

/**
 * Boat Service
 * 
 * Provides methods for handling CRUD operations for boat entities.
 * All methods communicate with the backend API and handle error responses.
 */
export const boatService = {
  /**
   * Retrieves all boats from the API.
   * 
   * @returns Promise resolving to an array of boat objects
   * @throws API error if the request fails
   */
  getAll: async (): Promise<Boat[]> => {
    try {
      const response = await api.get<Boat[]>('/boats');
      return response.data;
    } catch (error) {
      throw handleApiError(error);
    }
  },
  
  /**
   * Retrieves a specific boat by its ID.
   * 
   * @param id - The unique identifier of the boat to retrieve
   * @returns Promise resolving to the requested boat object
   * @throws API error if the request fails or boat is not found
   */
  getById: async (id: number): Promise<Boat> => {
    try {
      const response = await api.get<Boat>(`/boats/${id}`);
      return response.data;
    } catch (error) {
      throw handleApiError(error);
    }
  },
  
  /**
   * Creates a new boat entity.
   * 
   * @param boat - The boat data to create (without ID)
   * @returns Promise resolving to the created boat with assigned ID
   * @throws API error if the request fails or validation errors occur
   */
  create: async (boat: Omit<Boat, 'id'>): Promise<Boat> => {
    try {
      const response = await api.post<Boat>('/boats', boat);
      return response.data;
    } catch (error) {
      throw handleApiError(error);
    }
  },
  
  /**
   * Updates an existing boat entity.
   * 
   * @param id - The unique identifier of the boat to update
   * @param boat - The updated boat data
   * @returns Promise resolving to the updated boat object
   * @throws API error if the request fails, boat is not found, or validation errors occur
   */
  update: async (id: number, boat: Boat): Promise<Boat> => {
    try {
      const response = await api.put<Boat>(`/boats/${id}`, boat);
      return response.data;
    } catch (error) {
      throw handleApiError(error);
    }
  },
  
  /**
   * Deletes a boat entity.
   * 
   * @param id - The unique identifier of the boat to delete
   * @throws API error if the request fails or boat is not found
   */
  delete: async (id: number): Promise<void> => {
    try {
      await api.delete(`/boats/${id}`);
    } catch (error) {
      throw handleApiError(error);
    }
  }
}; 