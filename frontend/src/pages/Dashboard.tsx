import { useEffect, useState } from 'react';
import { Boat } from '@/types/boat';
import { BoatForm } from '@/components/BoatForm';
import { Button } from '@/components/ui/button';
import { PlusIcon, Pencil1Icon, TrashIcon } from '@radix-ui/react-icons';
import { ApiError, ValidationErrors } from '@/types/api';
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { motion } from "framer-motion";
import { boatService } from '@/services';

const Dashboard = () => {
  const [boats, setBoats] = useState<Boat[]>([]);
  const [selectedBoat, setSelectedBoat] = useState<Boat | null>(null);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [validationErrors, setValidationErrors] = useState<ValidationErrors | null>(null);
  const [showDetailDialog, setShowDetailDialog] = useState(false);

  useEffect(() => {
    fetchBoats();
  }, []);

  const fetchBoats = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await boatService.getAll();
      setBoats(data);
    } catch (err) {
      const apiError = err as ApiError;
      setError(apiError.message || 'Erreur lors du chargement des bateaux');
    } finally {
      setLoading(false);
    }
  };

  const handleAddBoat = () => {
    setSelectedBoat(null);
    setShowForm(true);
    setValidationErrors(null);
    setError(null);
  };

  const handleEditBoat = (boat: Boat) => {
    setSelectedBoat(boat);
    setShowForm(true);
    setValidationErrors(null);
    setError(null);
  };

  const handleDeleteBoat = async (id: number) => {
    if (window.confirm('Êtes-vous sûr de vouloir supprimer ce bateau ?')) {
      setError(null);
      try {
        await boatService.delete(id);
        setBoats(boats.filter(boat => boat.id !== id));
      } catch (err) {
        const apiError = err as ApiError;
        setError(apiError.message || 'Erreur lors de la suppression du bateau');
      }
    }
  };

  const handleSubmit = async (boat: Omit<Boat, 'id'> | Boat) => {
    setError(null);
    setValidationErrors(null);
    try {
      if ('id' in boat) {
        // Mise à jour d'un bateau existant
        const updatedBoat = await boatService.update(boat.id, boat as Boat);
        setBoats(boats.map(b => (b.id === updatedBoat.id ? updatedBoat : b)));
        setShowForm(false);
      } else {
        // Création d'un nouveau bateau
        const newBoat = await boatService.create(boat);
        setBoats([...boats, newBoat]);
        setShowForm(false);
      }
    } catch (err) {
      const apiError = err as ApiError;
      setError(apiError.message || 'Erreur lors de l\'enregistrement du bateau');
      
      // Si nous avons des erreurs de validation, les stocker pour les afficher dans le formulaire
      if (apiError.validationErrors) {
        setValidationErrors(apiError.validationErrors);
      }
      
      console.error(apiError);
    }
  };

  const handleRowClick = (boat: Boat) => {
    setSelectedBoat(boat);
    setShowDetailDialog(true);
  };

  const handleCancelForm = () => {
    setShowForm(false);
    setSelectedBoat(null);
    setError(null);
    setValidationErrors(null);
  };

  if (loading && boats.length === 0) {
    return (
      <div className="flex items-center justify-center h-[calc(100vh-64px)]">
        <div className="loading loading-lg"></div>
      </div>
    );
  }

  return (
    <motion.div 
      initial={{ opacity: 0 }} 
      animate={{ opacity: 1 }} 
      transition={{ duration: 0.5 }}
      className="container mx-auto px-4 py-8"
    >
      <div className="flex justify-between items-center mb-8">
        <motion.h1 
          initial={{ x: -20, opacity: 0 }} 
          animate={{ x: 0, opacity: 1 }} 
          transition={{ delay: 0.2 }}
          className="text-2xl font-bold"
        >
          Liste des bateaux
        </motion.h1>
        <motion.div 
          initial={{ x: 20, opacity: 0 }} 
          animate={{ x: 0, opacity: 1 }} 
          transition={{ delay: 0.3 }}
        >
          <Button onClick={handleAddBoat} className="flex items-center">
            <PlusIcon className="mr-2 h-4 w-4" />
            Ajouter un bateau
          </Button>
        </motion.div>
      </div>

      {error && (
        <motion.div
          initial={{ opacity: 0, y: -10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.3 }}
        >
          <Alert variant="destructive" className="mb-4">
            <AlertDescription>{error}</AlertDescription>
          </Alert>
        </motion.div>
      )}

      {showForm ? (
        <motion.div 
          initial={{ opacity: 0 }} 
          animate={{ opacity: 1 }} 
          transition={{ duration: 0.3 }}
          className="mb-8"
        >
          <BoatForm
            boat={selectedBoat || undefined}
            onSubmit={handleSubmit}
            onCancel={handleCancelForm}
            validationErrors={validationErrors}
          />
        </motion.div>
      ) : (
        boats.length === 0 ? (
          <motion.div 
            initial={{ opacity: 0, scale: 0.95 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.3 }}
            className="text-center py-12 bg-card rounded-lg border border-border"
          >
            <p className="text-muted-foreground">Aucun bateau à afficher</p>
            <Button onClick={handleAddBoat} variant="link" className="mt-2">
              Ajouter votre premier bateau
            </Button>
          </motion.div>
        ) : (
          <motion.div 
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.3 }}
            className="rounded-md border border-border"
          >
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead className="w-[200px]">Nom</TableHead>
                  <TableHead>Description</TableHead>
                  <TableHead className="text-right w-[150px]">Actions</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {boats.map((boat, index) => (
                  <motion.tr 
                    key={boat.id} 
                    className="cursor-pointer hover:bg-muted/50 transition-colors duration-200"
                    onClick={() => handleRowClick(boat)}
                    initial={{ opacity: 0, y: 10 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ delay: index * 0.05, duration: 0.3 }}
                  >
                    <TableCell className="font-medium">{boat.name}</TableCell>
                    <TableCell className="truncate max-w-[400px] break-all break-words">{boat.description}</TableCell>
                    <TableCell className="text-right">
                      <div className="flex justify-end gap-2">
                        <Button 
                          size="sm" 
                          variant="ghost" 
                          onClick={(e) => {
                            e.stopPropagation();
                            handleEditBoat(boat);
                          }}
                          className="hover:bg-muted transition-colors duration-200"
                        >
                          <Pencil1Icon className="h-4 w-4" />
                          <span className="sr-only">Modifier</span>
                        </Button>
                        <Button 
                          size="sm" 
                          variant="destructive" 
                          onClick={(e) => {
                            e.stopPropagation();
                            handleDeleteBoat(boat.id);
                          }}
                          className="transition-colors duration-200"
                        >
                          <TrashIcon className="h-4 w-4" />
                          <span className="sr-only">Supprimer</span>
                        </Button>
                      </div>
                    </TableCell>
                  </motion.tr>
                ))}
              </TableBody>
            </Table>
          </motion.div>
        )
      )}

      <Dialog open={showDetailDialog} onOpenChange={setShowDetailDialog}>
        <DialogContent className="sm:max-w-md bg-card">
          <DialogHeader>
            <DialogTitle>{selectedBoat?.name}</DialogTitle>
            <DialogDescription>
              Détails du bateau
            </DialogDescription>
          </DialogHeader>
          
          {selectedBoat && (
            <div className="flex flex-col h-full">
              <div className="flex-1 overflow-hidden py-4">
                <div className="space-y-2">
                  <h4 className="font-medium text-sm">Nom</h4>
                  <p>{selectedBoat.name}</p>
                </div>
                <div className="space-y-2 mt-4">
                  <h4 className="font-medium text-sm">Description</h4>
                  <div className="max-h-[120px] overflow-y-auto pr-2">
                    <p className="text-sm text-muted-foreground break-all break-words">{selectedBoat.description}</p>
                  </div>
                </div>
              </div>
              <div className="border-t border-border pt-4 mt-4">
                <div className="flex justify-end gap-2">
                  <Button 
                    variant="outline" 
                    onClick={() => setShowDetailDialog(false)}
                  >
                    Fermer
                  </Button>
                  <Button 
                    onClick={() => {
                      setShowDetailDialog(false);
                      handleEditBoat(selectedBoat);
                    }}
                  >
                    Modifier
                  </Button>
                </div>
              </div>
            </div>
          )}
        </DialogContent>
      </Dialog>
    </motion.div>
  );
};

export default Dashboard; 