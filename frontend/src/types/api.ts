export interface ValidationErrors {
  [key: string]: string;
}

export interface ApiError {
  message: string;
  validationErrors?: ValidationErrors;
  status: number;
} 