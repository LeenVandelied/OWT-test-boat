/**
 * Login Request Interface
 * 
 * Represents the credentials data sent to the server during login.
 * Used in authentication forms and API requests.
 */
export interface LoginRequest {
  /** Username for authentication */
  username: string;
  
  /** Password for authentication */
  password: string;
}

/**
 * Login Response Interface
 * 
 * Represents the response data from a successful login.
 * Contains the JWT token needed for authenticated requests.
 */
export interface LoginResponse {
  /** JWT authentication token */
  token: string;
  
  /** Token type (typically 'Bearer') */
  type: string;
} 