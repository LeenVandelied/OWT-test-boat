/**
 * Boat Interface
 * 
 * Represents a boat entity in the application.
 * Defines the shape of boat data used throughout the frontend.
 */
export interface Boat {
  /** Unique identifier for the boat */
  id: number;
  
  /** Name of the boat */
  name: string;
  
  /** Description or details about the boat */
  description: string;
} 