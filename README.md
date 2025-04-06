# Boat Manager

## Français

J'ai développé cette application de gestion de bateaux avec une architecture hexagonale en Spring Boot pour le backend et React pour le frontend dans le cadre de ce test technique.

## 📋 Table des matières

- [Installation](#installation)
- [Démarrage](#démarrage)
- [Tests](#tests)
- [Architecture](#architecture)
- [API](#api)
- [Sécurité](#sécurité)
- [Choix techniques](#choix-techniques)
- [Librairies utilisées](#librairies-utilisées)
- [Utilisation de l'IA](#utilisation-de-lia)
- [Améliorations potentielles](#améliorations-potentielles)

## 🚀 Installation

### Prérequis

- Java 17+
- Node.js 16+
- Maven 3.8+

### Backend

```bash
cd backend
mvn clean install
```

### Frontend

```bash
cd frontend
npm install
npm run build
```

Le build génère une version optimisée du frontend dans le dossier `dist/`.

## 🏁 Démarrage

### Backend

```bash
cd backend
mvn spring-boot:run
```

Le backend démarre sur `http://localhost:8080`.

### Frontend

Pour le développement :
```bash
cd frontend
npm run dev
```

Le frontend démarre sur `http://localhost:5173` (ou un autre port si 5173 est déjà utilisé).

Pour la production (après le build) :
```bash
cd frontend
npm run preview
```

### Authentification

Pour se connecter à l'application, utilisez les identifiants suivants :

- **Nom d'utilisateur :** admin
- **Mot de passe :** password

## 🧪 Tests

### Backend

```bash
cd backend
mvn test
```

### Frontend

```bash
cd frontend
npm test
```

Pour exécuter les tests en mode watch :

```bash
npm run test:watch
```

## 🏗️ Architecture

J'ai choisi de suivre l'architecture hexagonale (également connue sous le nom de "Ports & Adapters") pour ce projet.

### Architecture Hexagonale

L'architecture hexagonale organise le code en couches concentriques :

1. **Domaine** (centre) : Contient la logique métier et les modèles avec leurs règles.
2. **Application** : Contient les cas d'utilisation qui orchestrent le domaine.
3. **Infrastructure** (périphérie) : Contient tous les aspects techniques (persistance, API, UI).

#### Structure du Backend

```
com.owt.boat_test/
├── domain/
│   ├── models/         # Modèles du domaine
│   └── ports/          # Interfaces pour les dépendances externes
│       └── repositories/
├── application/
│   ├── services/       # Cas d'utilisation et orchestration
│   ├── dtos/           # Objets de transfert de données
│   └── mappers/        # Convertisseurs domaine <-> DTO
└── infrastructure/
    ├── adapters/       # Implémentation des ports
    │   ├── controllers/
    │   └── repositories/
    ├── entities/       # Entités JPA
    ├── mappers/        # Convertisseurs domaine <-> entité
    └── security/       # Configuration de sécurité
```

#### Structure du Frontend

Le frontend suit une architecture basée sur les composants avec React :

```
frontend/src/
├── components/         # Composants réutilisables
├── context/           # Contextes React pour l'état global
├── pages/             # Composants de page
├── services/          # Services d'API et utilitaires
└── types/             # Types et interfaces TypeScript
```

## 📡 API

### Endpoints disponibles

#### Authentification

| Méthode | Endpoint      | Description          | Corps de requête                               | Réponse                       |
|---------|---------------|----------------------|-----------------------------------------------|-------------------------------|
| POST    | /auth/login   | Authentification     | `{ "username": "...", "password": "..." }`    | `{ "token": "...", "type": "Bearer" }` |

#### Bateaux

| Méthode | Endpoint      | Description                | Corps de requête                                 | Réponse                      |
|---------|---------------|----------------------------|--------------------------------------------------|------------------------------|
| GET     | /boats        | Récupérer tous les bateaux | -                                                | `[{ "id": 1, "name": "...", "description": "..." }, ...]` |
| GET     | /boats/{id}   | Récupérer un bateau par ID | -                                                | `{ "id": 1, "name": "...", "description": "..." }` |
| POST    | /boats        | Créer un bateau            | `{ "name": "...", "description": "..." }`        | `{ "id": 1, "name": "...", "description": "..." }` |
| PUT     | /boats/{id}   | Mettre à jour un bateau    | `{ "id": 1, "name": "...", "description": "..." }` | `{ "id": 1, "name": "...", "description": "..." }` |
| DELETE  | /boats/{id}   | Supprimer un bateau        | -                                                | - |

## 🔒 Sécurité

J'ai mis en place une sécurité basée sur JWT (JSON Web Tokens) pour l'authentification :

1. **Authentification** : L'utilisateur s'authentifie via `/auth/login` et reçoit un token JWT.
2. **Autorisation** : Chaque requête ultérieure doit inclure ce token dans l'en-tête `Authorization: Bearer {token}`.
3. **Sécurité des endpoints** : Tous les endpoints `/boats/**` sont sécurisés et nécessitent un token valide.

#### Configuration de sécurité

- JWT secret key : `secretKeyForTestOnly` (inclus directement dans le code pour faciliter le test)
- Durée de validité du token : 1 heure

## 🛠️ Choix techniques

### Organisation en Mono Repo

J'ai fait le choix d'organiser ce projet sous forme de mono-repo pour plusieurs raisons :

1. **Simplicité de gestion** : Un seul dépôt Git facilite la maintenance, le versionnement et la synchronisation entre le frontend et le backend.
2. **Cohérence** : Cette approche assure que les modifications qui affectent à la fois le frontend et le backend soient commises ensemble, garantissant leur compatibilité.
3. **CI/CD simplifiée** : Un seul pipeline d'intégration/déploiement continu pour l'ensemble de l'application.
4. **Développement simplifié** : Plus facile pour les développeurs de comprendre l'ensemble du projet et de travailler sur les deux parties simultanément.
5. **Tests d'intégration** : Facilite la mise en place de tests qui couvrent l'ensemble de la pile technologique.

Pour un projet plus important avec des équipes distinctes travaillant sur le frontend et le backend, une approche multi-repos pourrait être préférable.

### Backend

#### Non-utilisation d'interfaces pour les services

J'ai choisi de ne pas utiliser d'interfaces pour les services afin de simplifier le projet. Dans une architecture hexagonale complète, les services devraient idéalement être définis par des interfaces dans le domaine, puis implémentés dans la couche application. Cependant, pour ce test technique, j'ai privilégié la simplicité et la lisibilité du code.

#### Non-utilisation de MapStruct

Bien que MapStruct soit un outil puissant pour le mapping entre objets, j'ai préféré utiliser des mappeurs manuels pour plus de transparence et pour éviter l'ajout d'une dépendance supplémentaire dans ce projet de démonstration.

#### Utilisateur en mémoire vs BDD

Pour simplifier l'installation et l'exécution du projet, j'ai opté pour un utilisateur en dur (in-memory) plutôt que stocké en base de données. Dans un environnement de production, les utilisateurs seraient naturellement stockés en base de données avec des mots de passe hachés.

#### H2 au lieu de PostgreSQL

Pour faciliter l'exécution du projet sans installation supplémentaire, j'ai utilisé H2, une base de données en mémoire. Cela permet également au projet d'être facilement exécuté et testé sans configuration préalable d'une base de données externe. Dans un environnement de production, une base de données robuste comme PostgreSQL serait préférable.

## 📚 Librairies utilisées

### Backend

- **Spring Boot** (2.7.x) - Framework de base pour l'application Java
- **Spring Security** - Pour la gestion de l'authentification et de l'autorisation
- **Spring Data JPA** - Pour la persistance des données
- **JJWT (Java JWT)** - Pour la création et validation des tokens JWT
- **H2 Database** - Base de données en mémoire
- **Lombok** - Pour réduire le code boilerplate (getters, setters, etc.)
- **Spring Boot Test** - Pour les tests unitaires et d'intégration
- **JUnit 5** - Framework de test
- **Mockito** - Framework de mock pour les tests

### Frontend

- **React** (18.x) - Bibliothèque UI
- **TypeScript** - Pour le typage statique
- **Vite** - Outil de build moderne et rapide
- **Axios** - Client HTTP pour les requêtes API
- **React Router** - Pour la navigation
- **Framer Motion** - Pour les animations
- **Tailwind CSS** - Framework CSS utilitaire
- **Shadcn UI** - Composants UI basés sur Radix UI
- **React Hook Form** - Gestion des formulaires
- **Zod** - Validation de schéma
- **Vitest** - Framework de test moderne
- **React Testing Library** - Utilitaires de test pour React

## 🤖 Utilisation de l'IA

Pour ce projet, j'ai expérimenté avec différents niveaux d'assistance par IA en profitant de la note "•Use appropriate tools, libraries, and frameworks to make your life easy." :

### Branches du projet

- **main_without_AI** : Version fonctionnelle du projet développée sans assistance d'IA (hormis l'autocomplétion de code intégrée à l'IDE). Vous pouvez récupérer cette branche pour tester la version de base du projet.

- **main** : Version améliorée avec l'aide de l'IA, incluant :
  - Tests unitaires plus complets (tous relus et vérifiés manuellement)
  - Documentation enrichie et plus claire
  - Améliorations du design et de l'expérience utilisateur

### Apports de l'IA

J'ai principalement utilisé l'IA pour :
1. **Tests unitaires** : Génération de cas de test plus complets
2. **Documentation** : Amélioration de la clarté et de l'exhaustivité des commentaires et du README
3. **Design frontend** : Suggestions pour l'amélioration de l'interface utilisateur, notamment pour les animations et l'accessibilité

### Approche

Pour chaque contribution générée par l'IA, j'ai suivi un processus de validation rigoureux :
- Relecture complète du code pour comprendre son fonctionnement
- Vérification de la cohérence avec l'architecture existante
- Tests manuels pour valider le comportement
- Adaptation si nécessaire pour respecter les conventions du projet

Cette approche m'a permis d'utiliser l'IA comme un outil d'amplification de productivité tout en conservant le contrôle sur la qualité et la cohérence du code.

## 🚀 Améliorations potentielles

Dans un contexte de production réelle, plusieurs améliorations seraient pertinentes, même pour un projet de cette taille :

### Architecture et design

- **Interfaces pour les services** : Introduire des interfaces pour tous les services dans la couche domaine, conformément aux principes stricts de l'architecture hexagonale, afin d'améliorer la testabilité et le découplage.

- **Utilisation de MapStruct** : Implémenter MapStruct pour le mapping entre objets afin de réduire le code boilerplate et améliorer la maintenabilité des convertisseurs d'objets.

### Persistance et sécurité

- **Utilisateurs en base de données** : Stocker les utilisateurs dans la base de données avec des mots de passe hachés, plutôt qu'en mémoire, pour une gestion plus robuste et évolutive des utilisateurs.

- **Migration vers PostgreSQL** : Remplacer H2 par PostgreSQL pour une solution de base de données plus robuste, particulièrement pour les environnements de production.

- **Variables d'environnement** : Externaliser les tokens, clés secrètes et autres constantes sensibles dans un fichier `.env` pour faciliter la configuration et renforcer la sécurité.

### Infrastructure et déploiement

- **Docker et docker-compose** : Conteneuriser l'application pour faciliter le déploiement et assurer la cohérence entre les environnements.

- **Pipeline CI/CD** : Mettre en place un pipeline d'intégration et de déploiement continus pour automatiser les tests et le déploiement.

- **Logging et monitoring** : Implémenter une solution complète de logging et de monitoring pour faciliter le debugging et l'analyse des performances.

### Fonctionnalités

- **Pagination et filtres** : Ajouter la pagination et des filtres pour les endpoints qui retournent des listes, afin d'améliorer les performances avec de grands volumes de données.

- **Validation plus robuste** : Renforcer la validation des entrées utilisateur avec des règles métier plus strictes et une gestion d'erreurs plus détaillée.

- **Swagger/OpenAPI** : Intégrer la documentation API automatique pour faciliter l'utilisation et le test des endpoints.

---

## English

# Boat Manager

I developed this boat management application with a hexagonal architecture in Spring Boot for the backend and React for the frontend for this technical test.

## 📋 Table of Contents

- [Installation](#installation-1)
- [Getting Started](#getting-started)
- [Tests](#tests-1)
- [Architecture](#architecture-1)
- [API](#api-1)
- [Security](#security)
- [Technical Choices](#technical-choices)
- [Libraries Used](#libraries-used)
- [AI Usage](#ai-usage)
- [Potential Improvements](#potential-improvements)

## 🚀 Installation

### Prerequisites

- Java 17+
- Node.js 16+
- Maven 3.8+

### Backend

```bash
cd backend
mvn clean install
```

### Frontend

```bash
cd frontend
npm install
npm run build
```

The build generates an optimized version of the frontend in the `dist/` folder.

## 🏁 Getting Started

### Backend

```bash
cd backend
mvn spring-boot:run
```

The backend starts on `http://localhost:8080`.

### Frontend

For development:
```bash
cd frontend
npm run dev
```

The frontend starts on `http://localhost:5173` (or another port if 5173 is already in use).

For production (after building):
```bash
cd frontend
npm run preview
```

### Authentication

To log in to the application, use the following credentials:

- **Username:** admin
- **Password:** password

## 🧪 Tests

### Backend

```bash
cd backend
mvn test
```

### Frontend

```bash
cd frontend
npm test
```

To run tests in watch mode:

```bash
npm run test:watch
```

## 🏗️ Architecture

I chose to follow the hexagonal architecture (also known as "Ports & Adapters") for this project.

### Hexagonal Architecture

The hexagonal architecture organizes code in concentric layers:

1. **Domain** (center): Contains business logic and models with their rules.
2. **Application**: Contains use cases that orchestrate the domain.
3. **Infrastructure** (periphery): Contains all technical aspects (persistence, API, UI).

#### Backend Structure

```
com.owt.boat_test/
├── domain/
│   ├── models/         # Domain models
│   └── ports/          # Interfaces for external dependencies
│       └── repositories/
├── application/
│   ├── services/       # Use cases and orchestration
│   ├── dtos/           # Data Transfer Objects
│   └── mappers/        # Domain <-> DTO converters
└── infrastructure/
    ├── adapters/       # Port implementations
    │   ├── controllers/
    │   └── repositories/
    ├── entities/       # JPA entities
    ├── mappers/        # Domain <-> Entity converters
    └── security/       # Security configuration
```

#### Frontend Structure

The frontend follows a component-based architecture with React:

```
frontend/src/
├── components/         # Reusable components
├── context/           # React contexts for global state
├── pages/             # Page components
├── services/          # API services and utilities
└── types/             # TypeScript types and interfaces
```

## 📡 API

### Available Endpoints

#### Authentication

| Method | Endpoint      | Description          | Request Body                                 | Response                      |
|--------|---------------|----------------------|----------------------------------------------|-------------------------------|
| POST   | /auth/login   | Authentication       | `{ "username": "...", "password": "..." }`   | `{ "token": "...", "type": "Bearer" }` |

#### Boats

| Method | Endpoint      | Description               | Request Body                                   | Response                     |
|--------|---------------|---------------------------|------------------------------------------------|------------------------------|
| GET    | /boats        | Retrieve all boats        | -                                              | `[{ "id": 1, "name": "...", "description": "..." }, ...]` |
| GET    | /boats/{id}   | Retrieve a boat by ID     | -                                              | `{ "id": 1, "name": "...", "description": "..." }` |
| POST   | /boats        | Create a boat             | `{ "name": "...", "description": "..." }`      | `{ "id": 1, "name": "...", "description": "..." }` |
| PUT    | /boats/{id}   | Update a boat             | `{ "id": 1, "name": "...", "description": "..." }` | `{ "id": 1, "name": "...", "description": "..." }` |
| DELETE | /boats/{id}   | Delete a boat             | -                                              | - |

## 🔒 Security

I implemented security based on JWT (JSON Web Tokens) for authentication:

1. **Authentication**: The user authenticates via `/auth/login` and receives a JWT token.
2. **Authorization**: Each subsequent request must include this token in the `Authorization: Bearer {token}` header.
3. **Endpoint Security**: All `/boats/**` endpoints are secured and require a valid token.

#### Security Configuration

- JWT secret key: `secretKeyForTestOnly` (included directly in the code for testing purposes)
- Token validity duration: 1 hour

## 🛠️ Technical Choices

### Organisation en Mono Repo

I chose to organize this project as a monorepo for several reasons:

1. **Management Simplicity**: A single Git repository simplifies maintenance, versioning, and synchronization between frontend and backend.
2. **Consistency**: This approach ensures that changes affecting both frontend and backend are committed together, guaranteeing their compatibility.
3. **Simplified CI/CD**: A single continuous integration/deployment pipeline for the entire application.
4. **Simplified Development**: Easier for developers to understand the entire project and work on both parts simultaneously.
5. **Integration Testing**: Facilitates the implementation of tests that cover the entire technology stack.

For a larger project with separate teams working on the frontend and backend, a multi-repo approach might be preferable.

### Backend

#### No Interfaces for Services

I chose not to use interfaces for services to simplify the project. In a complete hexagonal architecture, services should ideally be defined by interfaces in the domain layer and then implemented in the application layer. However, for this technical test, I prioritized simplicity and code readability.

#### No MapStruct

Although MapStruct is a powerful tool for object mapping, I preferred to use manual mappers for greater transparency and to avoid adding an additional dependency in this demonstration project.

#### In-Memory User vs Database

To simplify installation and execution of the project, I opted for a hardcoded (in-memory) user rather than one stored in the database. In a production environment, users would naturally be stored in a database with hashed passwords.

#### H2 Instead of PostgreSQL

To facilitate running the project without additional installation, I used H2, an in-memory database. This also allows the project to be easily run and tested without prior configuration of an external database. In a production environment, a robust database like PostgreSQL would be preferable.

## 📚 Libraries Used

### Backend

- **Spring Boot** (2.7.x) - Core framework for Java application
- **Spring Security** - For authentication and authorization management
- **Spring Data JPA** - For data persistence
- **JJWT (Java JWT)** - For creating and validating JWT tokens
- **H2 Database** - In-memory database
- **Lombok** - To reduce boilerplate code (getters, setters, etc.)
- **Spring Boot Test** - For unit and integration tests
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework for tests

### Frontend

- **React** (18.x) - UI library
- **TypeScript** - For static typing
- **Vite** - Modern and fast build tool
- **Axios** - HTTP client for API requests
- **React Router** - For navigation
- **Framer Motion** - For animations
- **Tailwind CSS** - Utility-first CSS framework
- **Shadcn UI** - UI components based on Radix UI
- **React Hook Form** - Form management
- **Zod** - Schema validation
- **Vitest** - Modern testing framework
- **React Testing Library** - Testing utilities for React

## 🤖 AI Usage

For this project, I experimented with different levels of AI assistance as the note "•Use appropriate tools, libraries, and frameworks to make your life easy." allow it :
### Project Branches

- **main_without_AI**: Functional version of the project developed without AI assistance (except for IDE-integrated code autocompletion). You can check out this branch to test the base version of the project.

- **main**: Enhanced version with AI assistance, including:
  - More comprehensive unit tests (all manually reviewed and verified)
  - Enriched and clearer documentation
  - Improvements in design and user experience

### AI Contributions

I primarily used AI for:
1. **Unit tests**: Generation of more comprehensive test cases, particularly for edge-case scenarios
2. **Documentation**: Improving clarity and comprehensiveness of comments and README
3. **Frontend design**: Suggestions for improving the user interface, particularly animations and accessibility

### Approach

For each AI-generated contribution, I followed a rigorous validation process:
- Complete code review to understand its operation
- Verification of consistency with the existing architecture
- Manual testing to validate behavior
- Adaptation if necessary to respect project conventions

This approach allowed me to use AI as a productivity amplification tool while maintaining control over code quality and consistency.

## 🚀 Potential Improvements

In a real production context, several improvements would be relevant, even for a project of this size:

### Architecture and Design

- **Interfaces for services**: Introduce interfaces for all services in the domain layer, in accordance with strict principles of hexagonal architecture, to improve testability and decoupling.

- **MapStruct usage**: Implement MapStruct for object mapping to reduce boilerplate code and improve maintainability of object converters.

### Persistence and Security

- **Database users**: Store users in the database with hashed passwords, rather than in memory, for more robust and scalable user management.

- **Migration to PostgreSQL**: Replace H2 with PostgreSQL for a more robust database solution, particularly for production environments.

- **Environment variables**: Externalize tokens, secret keys, and other sensitive constants in a `.env` file to facilitate configuration and enhance security.

### Infrastructure and Deployment

- **Docker and docker-compose**: Containerize the application to facilitate deployment and ensure consistency between environments.

- **CI/CD pipeline**: Implement a continuous integration and deployment pipeline to automate testing and deployment.

- **Logging and monitoring**: Implement a comprehensive logging and monitoring solution to facilitate debugging and performance analysis.

### Features

- **Pagination and filters**: Add pagination and filters for endpoints that return lists, to improve performance with large volumes of data.

- **More robust validation**: Strengthen user input validation with stricter business rules and more detailed error handling.

- **Swagger/OpenAPI**: Integrate automatic API documentation to facilitate endpoint usage and testing.
