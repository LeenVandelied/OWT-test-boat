import { LoginRequest, LoginResponse } from '@/types/auth';
import api from './axiosConfig';
import { handleApiError } from './errorHandler';

/**
 * Authentication Service
 * 
 * Provides methods for handling user authentication operations including
 * login, logout, and authentication status verification.
 */
export const authService = {
  /**
   * Authenticates a user with the provided credentials.
   * Stores the JWT token in localStorage upon successful authentication.
   * 
   * @param credentials - Object containing username and password
   * @returns The JWT token received from the server
   * @throws API error if authentication fails
   */
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
  
  /**
   * Logs out the current user by removing their authentication token from storage.
   */
  logout: (): void => {
    localStorage.removeItem('token');
  },
  
  /**
   * Checks if the user is currently authenticated.
   * 
   * @returns True if the user has a valid token in localStorage, false otherwise
   */
  isAuthenticated: (): boolean => {
    return localStorage.getItem('token') !== null;
  }
}; 