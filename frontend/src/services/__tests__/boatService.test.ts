import { describe, it, expect, vi, beforeEach } from 'vitest';
import { boatService } from '../boatService';
import { Boat } from '@/types/boat';

vi.mock('../axiosConfig', () => ({
  default: {
    get: vi.fn(),
    post: vi.fn(),
    put: vi.fn(),
    delete: vi.fn()
  }
}));

import api from '../axiosConfig';

describe('boatService', () => {
  beforeEach(() => {
    vi.resetAllMocks();
  });

  describe('getAll', () => {
    it('should return boats when API call is successful', async () => {
      const mockBoats = [
        { id: 1, name: 'Boat 1', description: 'Description 1' },
        { id: 2, name: 'Boat 2', description: 'Description 2' }
      ];
      
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      vi.mocked(api.get).mockResolvedValue({ data: mockBoats } as any);

      const result = await boatService.getAll();

      expect(api.get).toHaveBeenCalledWith('/boats');
      expect(result).toEqual(mockBoats);
    });

    it('should throw error when API call fails', async () => {
      vi.mocked(api.get).mockRejectedValue(new Error('Network error'));

      await expect(boatService.getAll()).rejects.toThrow();
    });
  });

  describe('getById', () => {
    it('should return a boat when API call is successful', async () => {
      const mockBoat = { id: 1, name: 'Boat 1', description: 'Description 1' };
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      vi.mocked(api.get).mockResolvedValue({ data: mockBoat } as any);

      const result = await boatService.getById(1);

      expect(api.get).toHaveBeenCalledWith('/boats/1');
      expect(result).toEqual(mockBoat);
    });

    it('should throw error when API call fails', async () => {
      vi.mocked(api.get).mockRejectedValue(new Error('Network error'));

      await expect(boatService.getById(1)).rejects.toThrow();
    });
  });

  describe('create', () => {
    it('should return created boat when API call is successful', async () => {
      const newBoat: Omit<Boat, 'id'> = { 
        name: 'New Boat', 
        description: 'New Description' 
      };
      const createdBoat = { id: 3, ...newBoat };
      
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      vi.mocked(api.post).mockResolvedValue({ data: createdBoat } as any);

      const result = await boatService.create(newBoat);

      expect(api.post).toHaveBeenCalledWith('/boats', newBoat);
      expect(result).toEqual(createdBoat);
    });

    it('should throw error when API call fails', async () => {
      const newBoat: Omit<Boat, 'id'> = { 
        name: 'New Boat', 
        description: 'New Description' 
      };
      vi.mocked(api.post).mockRejectedValue(new Error('Network error'));

      await expect(boatService.create(newBoat)).rejects.toThrow();
    });
  });

  describe('update', () => {
    it('should return updated boat when API call is successful', async () => {
      const boat: Boat = { 
        id: 1, 
        name: 'Updated Boat', 
        description: 'Updated Description' 
      };
      const updatedBoat = { ...boat };
      
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      vi.mocked(api.put).mockResolvedValue({ data: updatedBoat } as any);

      const result = await boatService.update(boat.id, boat);

      expect(api.put).toHaveBeenCalledWith('/boats/1', boat);
      expect(result).toEqual(updatedBoat);
    });

    it('should throw error when API call fails', async () => {
      const boat: Boat = { 
        id: 1, 
        name: 'Updated Boat', 
        description: 'Updated Description' 
      };
      vi.mocked(api.put).mockRejectedValue(new Error('Network error'));

      await expect(boatService.update(boat.id, boat)).rejects.toThrow();
    });
  });

  describe('delete', () => {
    it('should return success when API call is successful', async () => {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      vi.mocked(api.delete).mockResolvedValue({ status: 204 } as any);

      await boatService.delete(1);

      expect(api.delete).toHaveBeenCalledWith('/boats/1');
    });

    it('should throw error when API call fails', async () => {
      vi.mocked(api.delete).mockRejectedValue(new Error('Network error'));

      await expect(boatService.delete(1)).rejects.toThrow();
    });
  });
}); 