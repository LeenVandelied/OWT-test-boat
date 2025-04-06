/**
 * Validation Errors Interface
 * 
 * Represents validation errors from the API.
 * Contains key-value pairs where:
 * - key: The field name that had a validation error
 * - value: The error message for that field
 */
export interface ValidationErrors {
  [key: string]: string;
}

/**
 * API Error Interface
 * 
 * Standardized error structure for API errors throughout the application.
 * Used to maintain consistent error handling and display.
 */
export interface ApiError {
  /** User-friendly error message */
  message: string;
  
  /** Optional validation errors for form fields */
  validationErrors?: ValidationErrors;
  
  /** HTTP status code of the error */
  status: number;
} 