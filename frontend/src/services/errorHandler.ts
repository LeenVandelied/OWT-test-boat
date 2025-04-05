import axios, { AxiosError } from 'axios';
import { ApiError, ValidationErrors } from '@/types/api';

export const handleApiError = (error: unknown): never => {
  if (axios.isAxiosError(error)) {
    const axiosError = error as AxiosError;
    const apiError: ApiError = {
      message: 'Une erreur est survenue',
      status: axiosError.response?.status || 500
    };

    if (axiosError.response) {
      if (axiosError.response.status === 400 && typeof axiosError.response.data === 'object') {
        apiError.message = 'Veuillez corriger les erreurs de validation';
        apiError.validationErrors = axiosError.response.data as ValidationErrors;
      } 
      else if (typeof axiosError.response.data === 'string') {
        apiError.message = axiosError.response.data;
      }
      else if (axiosError.response.data && typeof axiosError.response.data === 'object' && 'message' in axiosError.response.data) {
        apiError.message = (axiosError.response.data as { message: string }).message;
      }
    }

    throw apiError;
  }
  
  throw {
    message: 'Une erreur inconnue est survenue',
    status: 500
  } as ApiError;
}; 