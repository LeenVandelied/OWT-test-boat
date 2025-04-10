import axios, { AxiosError } from 'axios';
import { ApiError, ValidationErrors } from '@/types/api';

/**
 * API Error Handler
 * 
 * Processes errors from API requests and transforms them into standardized ApiError objects.
 * Handles different types of errors including authentication errors, validation errors,
 * and server errors, providing appropriate user-friendly messages.
 * 
 * @param error - The raw error from an API request
 * @returns Never returns normally, always throws a standardized ApiError
 * @throws ApiError with appropriate message and status
 */
export const handleApiError = (error: unknown): never => {
  if (axios.isAxiosError(error)) {
    const axiosError = error as AxiosError;
    const apiError: ApiError = {
      message: 'Une erreur est survenue',
      status: axiosError.response?.status || 500
    };

    if (axiosError.response) {
      const isLoginEndpoint = axiosError.config?.url?.includes('/login');
      
      // Handle authentication errors
      if (axiosError.response.status === 401) {
        if (isLoginEndpoint) {
          apiError.message = 'Identifiant ou mot de passe incorrect';
        } else {
          apiError.message = 'Votre session a expiré, veuillez vous reconnecter';
        }
      }
      // Handle authorization errors
      else if (axiosError.response.status === 403) {
        apiError.message = 'Vous n\'avez pas les droits nécessaires pour cette action';
      }
      // Handle validation errors (HTTP 400 with validation details)
      else if (axiosError.response.status === 400 && typeof axiosError.response.data === 'object') {
        apiError.message = 'Veuillez corriger les erreurs de validation';
        apiError.validationErrors = axiosError.response.data as ValidationErrors;
      } 
      // Handle simple string error messages
      else if (typeof axiosError.response.data === 'string') {
        apiError.message = axiosError.response.data;
      }
      // Handle object error messages with message property
      else if (axiosError.response.data && typeof axiosError.response.data === 'object' && 'message' in axiosError.response.data) {
        apiError.message = (axiosError.response.data as { message: string }).message;
      }
    }

    throw apiError;
  }
  
  // Handle non-Axios errors with a generic message
  throw {
    message: 'Une erreur inconnue est survenue',
    status: 500
  } as ApiError;
}; 