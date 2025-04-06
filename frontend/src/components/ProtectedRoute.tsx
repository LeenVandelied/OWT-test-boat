import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';
import { useEffect } from 'react';

/**
 * Protected Route Component
 * 
 * A route wrapper component that restricts access to authenticated users only.
 * Redirects unauthenticated users to the login page, preserving the original 
 * requested URL in the redirect state for potential redirect back after login.
 * 
 * Used as a wrapper for routes that require authentication.
 */
export const ProtectedRoute = () => {
  const { isAuthenticated, loading, checkAuthStatus } = useAuth();
  const location = useLocation();
  
  /**
   * Effect to verify authentication status when the component mounts
   * Ensures the authentication state is up-to-date before rendering protected content
   */
  useEffect(() => {
    checkAuthStatus();
  }, [checkAuthStatus]);

  // Show loading spinner while authentication state is being determined
  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <div className="animate-spin h-8 w-8 border-4 border-blue-600 rounded-full border-t-transparent"></div>
      </div>
    );
  }
  
  // Redirect to login page if not authenticated
  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  // Render the protected content if authenticated
  return <Outlet />;
}; 