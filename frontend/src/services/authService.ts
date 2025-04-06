import { LoginRequest, LoginResponse } from '@/types/auth';
import api from './axiosConfig';
import { handleApiError } from './errorHandler';

export const authService = {
  login: async (credentials: LoginRequest): Promise<string> => {
    try {
      const response = await api.post<LoginResponse>('/auth/login', credentials);
      const token = response.data.token;
      localStorage.setItem('token', token);
      return token;
    } catch (error) {
      throw handleApiError(error);
    }
  },
  
  logout: (): void => {
    localStorage.removeItem('token');
  },
  
  isAuthenticated: (): boolean => {
    return localStorage.getItem('token') !== null;
  }
}; 