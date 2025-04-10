import { Button } from './ui/button';
import { useAuth } from '@/context/AuthContext';
import { motion } from 'framer-motion';
import { AnchorIcon } from 'lucide-react';
import { ExitIcon } from '@radix-ui/react-icons';

/**
 * Header Component
 * 
 * Displays the application header with logo, title, and navigation elements.
 * Shows or hides the logout button based on authentication state.
 * Includes an animated anchor icon that swings gently.
 */
export const Header = () => {
  const { logout, isAuthenticated } = useAuth();

  return (
    <header className="bg-background border-b border-border sticky top-0 z-10 backdrop-blur-sm bg-opacity-90">
      <div className="container mx-auto px-4 h-16 flex items-center justify-between">
        {/* Logo and title section */}
        <div className="flex items-center space-x-2">
          <motion.div
            animate={{ rotate: [-10, 10, -10] }}
            transition={{ 
              repeat: Infinity, 
              duration: 6,
              ease: "easeInOut"
            }}
          >
            <AnchorIcon className="h-5 w-5 text-primary" />
          </motion.div>
          <h1 className="text-xl font-bold text-primary">Boat Manager</h1>
        </div>
        
        {/* Navigation and actions section */}
        <div className="flex items-center gap-2">
          {/* Only show logout button when user is authenticated */}
          {isAuthenticated && (
            <Button 
              variant="ghost" 
              onClick={logout}
              className="flex items-center"
            >
              <ExitIcon className="mr-2 h-4 w-4" />
              Déconnexion
            </Button>
          )}
        </div>
      </div>
    </header>
  );
}; 