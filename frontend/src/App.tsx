import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from '@/context/AuthContext';
import Login from '@/pages/Login';
import Dashboard from '@/pages/Dashboard';
import { ProtectedRoute } from '@/components/ProtectedRoute';
import { Header } from '@/components/Header';

/**
 * AppRoutes Component
 * 
 * Handles the routing logic for the application based on authentication state.
 * Redirects unauthenticated users to the login page and protects the dashboard route.
 */
const AppRoutes = () => {
  const { isAuthenticated } = useAuth();

  return (
    <div className="min-h-screen bg-background text-foreground">
      <Header />
      <Routes>
        <Route path="/login" element={
          isAuthenticated ? <Navigate to="/" replace /> : <Login />
        } />
        <Route element={<ProtectedRoute />}>
          <Route path="/" element={<Dashboard />} />
        </Route>
      </Routes>
    </div>
  );
};

/**
 * App Component
 * 
 * Main application component that sets up the authentication context and routing.
 * Wraps the entire application with necessary providers for authentication and navigation.
 */
function App() {
  return (
    <AuthProvider>
      <Router>
        <AppRoutes />
      </Router>
    </AuthProvider>
  );
}

export default App;
