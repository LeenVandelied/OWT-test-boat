import { Boat } from '@/types/boat';
import api from './axiosConfig';
import { handleApiError } from './errorHandler';

export const boatService = {
  getAll: async (): Promise<Boat[]> => {
    try {
      const response = await api.get<Boat[]>('/boats');
      return response.data;
    } catch (error) {
      throw handleApiError(error);
    }
  },
  
  getById: async (id: number): Promise<Boat> => {
    try {
      const response = await api.get<Boat>(`/boats/${id}`);
      return response.data;
    } catch (error) {
      throw handleApiError(error);
    }
  },
  
  create: async (boat: Omit<Boat, 'id'>): Promise<Boat> => {
    try {
      const response = await api.post<Boat>('/boats', boat);
      return response.data;
    } catch (error) {
      throw handleApiError(error);
    }
  },
  
  update: async (id: number, boat: Boat): Promise<Boat> => {
    try {
      const response = await api.put<Boat>(`/boats/${id}`, boat);
      return response.data;
    } catch (error) {
      throw handleApiError(error);
    }
  },
  
  delete: async (id: number): Promise<void> => {
    try {
      await api.delete(`/boats/${id}`);
    } catch (error) {
      throw handleApiError(error);
    }
  }
}; 