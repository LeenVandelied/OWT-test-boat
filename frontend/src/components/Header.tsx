import { Button } from './ui/button';
import { useAuth } from '@/context/AuthContext';
import { ExitIcon } from '@radix-ui/react-icons';

export const Header = () => {
  const { logout, isAuthenticated } = useAuth();

  return (
    <header className="bg-white border-b border-gray-200 sticky top-0 z-10">
      <div className="container mx-auto px-4 h-16 flex items-center justify-between">
        <div className="flex items-center space-x-4">
          <h1 className="text-xl font-bold text-blue-600">Boat Manager</h1>
        </div>
        
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
    </header>
  );
}; 