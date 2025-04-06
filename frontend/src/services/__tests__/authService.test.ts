import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import { authService } from '../authService';
import { LoginRequest } from '@/types/auth';

vi.mock('../axiosConfig', () => ({
  default: {
    post: vi.fn()
  }
}));

import api from '../axiosConfig';

describe('authService', () => {
  beforeEach(() => {
    const mockStorage: Record<string, string> = {};
    
    Storage.prototype.setItem = vi.fn((key, value) => {
      mockStorage[key] = String(value);
    });
    
    Storage.prototype.getItem = vi.fn((key) => {
      return mockStorage[key] || null;
    });
    
    Storage.prototype.removeItem = vi.fn((key) => {
      delete mockStorage[key];
    });
    
    vi.resetAllMocks();
  });

  afterEach(() => {
    vi.restoreAllMocks();
  });

  describe('login', () => {
    it('should save token to localStorage on successful login', async () => {
      vi.mocked(api.post).mockResolvedValue({
        data: { token: 'test-token' }
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      } as any);
      
      const loginData: LoginRequest = { 
        username: 'admin', 
        password: 'password' 
      };

      const result = await authService.login(loginData);

      expect(api.post).toHaveBeenCalledWith('/auth/login', loginData);
      expect(localStorage.setItem).toHaveBeenCalledWith('token', 'test-token');
      expect(result).toBe('test-token');
    });

    it('should throw error when login fails', async () => {
      vi.mocked(api.post).mockRejectedValue(new Error('Invalid credentials'));
      
      const loginData: LoginRequest = { 
        username: 'wronguser', 
        password: 'wrongpass' 
      };

      await expect(authService.login(loginData)).rejects.toThrow();
    });
  });

  describe('logout', () => {
    it('should remove token from localStorage', () => {
      authService.logout();
      
      expect(localStorage.removeItem).toHaveBeenCalledWith('token');
    });
  });

  describe('isAuthenticated', () => {
    it('should return true when token exists', () => {
      vi.mocked(localStorage.getItem).mockReturnValue('test-token');
      
      const result = authService.isAuthenticated();
      
      expect(result).toBe(true);
    });

    it('should return false when token does not exist', () => {
      vi.mocked(localStorage.getItem).mockReturnValue(null);
      
      const result = authService.isAuthenticated();
      
      expect(result).toBe(false);
    });
  });
}); 