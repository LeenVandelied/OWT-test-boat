import { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { LoginRequest } from '@/types/auth';
import { authService } from '@/services';

/**
 * Authentication Context Type Definition
 * 
 * Defines the shape of the authentication context with methods for login, logout,
 * and checking authentication status.
 */
interface AuthContextType {
  isAuthenticated: boolean;
  login: (credentials: LoginRequest) => Promise<void>;
  logout: () => void;
  loading: boolean;
  checkAuthStatus: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

/**
 * Authentication Provider Component
 * 
 * Manages authentication state and provides auth-related functions to the application.
 * Handles login, logout, and periodic token validation.
 * 
 * @param children - Child components that will have access to the auth context
 */
export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(true);

  /**
   * Verifies if the user is still authenticated by checking the token
   * Updates authentication state accordingly
   */
  const checkAuthStatus = () => {
    const auth = authService.isAuthenticated();
    setIsAuthenticated(auth);
    setLoading(false);
  };

  // Set up authentication check on component mount and at regular intervals
  useEffect(() => {
    checkAuthStatus();
    
    // Check auth status every 5 minutes to ensure token validity
    const intervalId = setInterval(checkAuthStatus, 5 * 60 * 1000);
    return () => clearInterval(intervalId);
  }, []);

  /**
   * Authenticates a user with the provided credentials
   * 
   * @param credentials - Object containing username and password
   */
  const login = async (credentials: LoginRequest) => {
    setLoading(true);
    try {
      await authService.login(credentials);
      setIsAuthenticated(true);
    } finally {
      setLoading(false);
    }
  };

  /**
   * Logs out the current user by removing their authentication token
   */
  const logout = () => {
    authService.logout();
    setIsAuthenticated(false);
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout, loading, checkAuthStatus }}>
      {children}
    </AuthContext.Provider>
  );
};

/**
 * Authentication Hook
 * 
 * Custom hook to access the authentication context throughout the application.
 * Ensures the hook is used within an AuthProvider component.
 * 
 * @returns Authentication context with auth state and methods
 * @throws Error if used outside of AuthProvider
 */
export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}; 