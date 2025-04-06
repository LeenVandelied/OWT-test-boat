import axios from 'axios';

/**
 * Base API URL for all backend requests
 */
const API_URL = 'http://localhost:8080';

/**
 * Axios instance configured with base URL and default headers.
 * This instance is used for all API requests in the application.
 */
const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * Request Interceptor
 * 
 * Automatically adds the authorization token to all outgoing requests.
 * The token is retrieved from localStorage and added as a Bearer token
 * in the Authorization header if it exists.
 */
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

/**
 * Response Interceptor
 * 
 * Handles global response processing, particularly authentication errors.
 * If a 401 (Unauthorized) or 403 (Forbidden) response is received for any
 * non-login request, the token is removed and the user is redirected to
 * the login page, effectively logging them out.
 */
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      if ((error.response.status === 401 || error.response.status === 403) && 
          !error.config.url.includes('/login')) {
        // Clear authentication on unauthorized responses
        localStorage.removeItem('token');
        
        // Redirect to login page if not already there
        if (!window.location.pathname.includes('/login')) {
          window.location.href = '/login';
        }
      }
    }
    return Promise.reject(error);
  }
);

export default api; 