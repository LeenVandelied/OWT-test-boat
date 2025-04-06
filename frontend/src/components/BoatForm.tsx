import { useState, useEffect } from 'react';
import { Boat } from '@/types/boat';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { ValidationErrors } from '@/types/api';
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { motion } from 'framer-motion';

interface BoatFormProps {
  boat?: Boat;
  onSubmit: (boat: Omit<Boat, 'id'> | Boat) => void;
  onCancel: () => void;
  validationErrors?: ValidationErrors | null;
}

interface FormErrors {
  name?: string;
  description?: string;
}

export const BoatForm = ({ boat, onSubmit, onCancel, validationErrors }: BoatFormProps) => {
  const [formData, setFormData] = useState<Omit<Boat, 'id'> | Boat>({
    name: '',
    description: '',
  });
  const [errors, setErrors] = useState<FormErrors>({});
  const [charactersLeft, setCharactersLeft] = useState(255);

  useEffect(() => {
    if (boat) {
      setFormData(boat);
      setCharactersLeft(255 - (boat.description?.length || 0));
    }
  }, [boat]);
  
  useEffect(() => {
    if (validationErrors) {
      const newErrors: FormErrors = {};
      
      if (validationErrors.name) {
        newErrors.name = validationErrors.name;
      }
      
      if (validationErrors.description) {
        newErrors.description = validationErrors.description;
      }
      
      setErrors(newErrors);
    }
  }, [validationErrors]);

  const validateForm = (): boolean => {
    const newErrors: FormErrors = {};
    let isValid = true;

    if (!formData.name || formData.name.trim().length < 2) {
      newErrors.name = "Le nom doit contenir au moins 2 caractères";
      isValid = false;
    } else if (formData.name.length > 100) {
      newErrors.name = "Le nom ne peut pas dépasser 100 caractères";
      isValid = false;
    }

    if (formData.description && formData.description.length > 255) {
      newErrors.description = "La description ne peut pas dépasser 255 caractères";
      isValid = false;
    }

    setErrors(newErrors);
    return isValid;
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    
    if (name === 'description') {
      setCharactersLeft(255 - value.length);
    }
    
    setFormData((prev) => ({ ...prev, [name]: value }));
    
    if (errors[name as keyof FormErrors]) {
      setErrors(prev => ({ ...prev, [name]: undefined }));
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (validateForm()) {
      onSubmit(formData);
    }
  };

  const isEditMode = !!boat?.id;
  const isDescriptionTooLong = charactersLeft < 0;

  return (
    <motion.div
      initial={{ opacity: 0, y: 10 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.3 }}
    >
      <Card className="w-full border-border bg-card">
        <CardHeader>
          <CardTitle>{isEditMode ? 'Modifier le bateau' : 'Ajouter un bateau'}</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            <motion.div 
              className="space-y-2"
              initial={{ opacity: 0, x: -10 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: 0.1, duration: 0.3 }}
            >
              <label htmlFor="name" className="text-sm font-medium">
                Nom
              </label>
              <Input
                id="name"
                name="name"
                required
                value={formData.name}
                onChange={handleInputChange}
                placeholder="Nom du bateau"
                className={`${errors.name ? "border-red-500" : "border-border"} bg-background`}
              />
              {errors.name && (
                <motion.p 
                  className="text-red-500 text-sm mt-1"
                  initial={{ opacity: 0, y: -5 }}
                  animate={{ opacity: 1, y: 0 }}
                >
                  {errors.name}
                </motion.p>
              )}
            </motion.div>
            <motion.div 
              className="space-y-2"
              initial={{ opacity: 0, x: -10 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: 0.2, duration: 0.3 }}
            >
              <label htmlFor="description" className="text-sm font-medium">
                Description
              </label>
              <div className="relative">
                <textarea
                  id="description"
                  name="description"
                  required
                  className={`flex h-20 w-full rounded-md border ${
                    isDescriptionTooLong || errors.description 
                      ? 'border-red-500' 
                      : 'border-border'
                  } bg-background text-foreground px-3 py-2 text-sm focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary focus-visible:ring-offset-2`}
                  value={formData.description}
                  onChange={handleInputChange}
                  placeholder="Description du bateau"
                />
                <div className={`text-sm mt-1 text-right ${
                  isDescriptionTooLong 
                    ? 'text-red-500 font-medium' 
                    : 'text-muted-foreground'
                }`}>
                  {charactersLeft} caractères restants
                </div>
              </div>
              {errors.description && (
                <motion.p 
                  className="text-red-500 text-sm mt-1"
                  initial={{ opacity: 0, y: -5 }}
                  animate={{ opacity: 1, y: 0 }}
                >
                  {errors.description}
                </motion.p>
              )}
            </motion.div>
          </form>
        </CardContent>
        <CardFooter className="flex justify-between border-t border-border pt-4">
          <Button 
            variant="outline" 
            onClick={onCancel}
          >
            Annuler
          </Button>
          <motion.div whileTap={{ scale: 0.98 }}>
            <Button 
              onClick={handleSubmit} 
              disabled={isDescriptionTooLong}
            >
              {isEditMode ? 'Enregistrer' : 'Ajouter'}
            </Button>
          </motion.div>
        </CardFooter>
      </Card>
    </motion.div>
  );
}; 