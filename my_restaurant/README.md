# 🍽️ My Restaurant - Site Clé en Main pour Restaurateurs

## ⚡ Démarrage Rapide

```bash
# 1. Créer la base de données MySQL
mysql -u root -p -e "CREATE DATABASE my_restaurant;"

# 2. Configurer application.properties avec vos identifiants MySQL

# 3. Lancer l'application
cd restaurant/my_restaurant
./gradlew bootRun

# 4. Accéder à l'application
# Site public : http://localhost:8080
# Admin : http://localhost:8080/admin/login
```

👉 **Pour les instructions détaillées, voir la section [Installation et Lancement](#-installation-et-lancement-du-projet)**

## 📋 Description

My Restaurant est une application web Spring Boot qui permet aux restaurateurs de créer facilement un site web professionnel pour leur établissement. L'application offre une solution "clé en main" avec une interface d'administration complète pour gérer le contenu du site.

## ✨ Fonctionnalités

### 🎯 Configuration Initiale

- **Setup automatique** : Redirection vers la page de configuration si le site n'est pas configuré
- **Personnalisation complète** :
  - Nom du restaurant
  - URL du site
  - Couleur dominante personnalisable
  - Upload de bannière
  - Adresse et informations de contact
  - Mot de passe administrateur sécurisé (validation : min 8 caractères, 1 minuscule, 1 majuscule, 1 chiffre)

### 🔧 Interface d'Administration

- **Dashboard complet** avec statistiques
- **Gestion des sections** (CRUD)
- **Gestion des plats** (CRUD) avec upload d'images
- **Gestion des menus** (CRUD)
- **Gestion des avis clients** (CRUD)
- **Interface moderne et responsive**

### 🌐 Site Public

- **Page d'accueil** avec informations du restaurant et avis récents
- **Carte du restaurant** organisée par sections
- **Page des menus** avec présentation détaillée
- **Système d'avis** avec notation par étoiles
- **Design responsive** et moderne

### ⭐ Système d'Avis

- **Formulaire d'avis** avec :
  - Nom du client (obligatoire)
  - Email (optionnel)
  - Note de 1 à 5 étoiles
  - Commentaire (optionnel)
  - Date automatique
- **Affichage des 4 derniers avis** sur la page d'accueil
- **Gestion complète** côté administration

## 🛠️ Technologies Utilisées

- **Backend** : Spring Boot 3.x
- **Base de données** : MySQL
- **Template Engine** : Thymeleaf
- **Sécurité** : Spring Security avec BCrypt
- **Upload de fichiers** : MultipartFile
- **Frontend** : HTML5, CSS3, JavaScript vanilla
- **Build Tool** : Gradle

## 🚀 Installation et Lancement du Projet

### Prérequis

- **Java 17** ou supérieur (vérifier avec `java -version`)
- **MySQL 8.0** ou supérieur
- **Gradle** (optionnel, le wrapper est inclus dans le projet)

### 📦 Étape 1 : Cloner le projet

```bash
git clone <url-du-repository>
cd restaurant-website/restaurant/my_restaurant
```

### 🗄️ Étape 2 : Configuration de la base de données MySQL

1. **Démarrer MySQL** (selon votre système) :

   ```bash
   # macOS avec Homebrew
   brew services start mysql

   # Linux
   sudo systemctl start mysql

   # Windows
   # Démarrer MySQL depuis les services Windows
   ```

2. **Se connecter à MySQL** :

   ```bash
   mysql -u root -p
   ```

3. **Créer la base de données** :
   ```sql
   CREATE DATABASE my_restaurant;
   EXIT;
   ```

### ⚙️ Étape 3 : Configuration de l'application

Modifiez le fichier `src/main/resources/application.properties` avec vos paramètres MySQL :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/my_restaurant
spring.datasource.username=root          # Votre nom d'utilisateur MySQL
spring.datasource.password=votre_mot_de_passe  # Votre mot de passe MySQL
```

**Note** : Si vous n'avez pas de mot de passe pour root, laissez `spring.datasource.password=` vide.

### 🚀 Étape 4 : Lancement de l'application

#### Option 1 : Avec Gradle Wrapper (Recommandé)

```bash
# Depuis le répertoire my_restaurant
./gradlew bootRun

# Sur Windows
gradlew.bat bootRun
```

#### Option 2 : Avec Gradle installé

```bash
gradle bootRun
```

#### Option 3 : Construire et exécuter le JAR

```bash
# Construire le projet
./gradlew build

# Exécuter le JAR
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

### 🌐 Étape 5 : Accès à l'application

Une fois l'application démarrée, vous verrez un message similaire à :

```
Started DemoApplication in X.XXX seconds
```

Accédez ensuite à :

- **Site public** : http://localhost:8080
- **Page de configuration initiale** : http://localhost:8080/setup (si le site n'est pas encore configuré)
- **Administration** : http://localhost:8080/admin/login

### 🔧 Configuration Initiale

Lors du premier lancement, vous serez redirigé vers la page de configuration (`/setup`) où vous devrez :

1. Entrer les informations du restaurant
2. Configurer la couleur dominante
3. Uploader une bannière
4. Définir un mot de passe administrateur (min 8 caractères, 1 minuscule, 1 majuscule, 1 chiffre)

### 🛑 Arrêter l'application

Pour arrêter l'application, utilisez `Ctrl + C` dans le terminal où elle s'exécute.

### 🔄 Réinitialiser la base de données

Si vous souhaitez réinitialiser la base de données, exécutez le script SQL :

```bash
mysql -u root -p my_restaurant < reset_database.sql
```

Ou manuellement dans MySQL :

```sql
USE my_restaurant;
SOURCE reset_database.sql;
```

### ❗ Dépannage (Troubleshooting)

#### Problème : Erreur de connexion à MySQL

```
Communications link failure
```

**Solution** :

- Vérifiez que MySQL est bien démarré
- Vérifiez les identifiants dans `application.properties`
- Vérifiez que la base de données `my_restaurant` existe

#### Problème : Port 8080 déjà utilisé

```
Port 8080 is already in use
```

**Solution** :

- Changez le port dans `application.properties` :
  ```properties
  server.port=8081
  ```
- Ou arrêtez l'application qui utilise le port 8080

#### Problème : Erreur "Gradle wrapper not found"

**Solution** :

- Assurez-vous d'être dans le répertoire `my_restaurant`
- Vérifiez que les fichiers `gradlew` (ou `gradlew.bat` sur Windows) existent
- Si nécessaire, installez Gradle et utilisez `gradle bootRun`

#### Problème : Erreur Java version

```
Unsupported class file major version XX
```

**Solution** :

- Vérifiez votre version Java : `java -version`
- Installez Java 17 ou supérieur
- Configurez `JAVA_HOME` si nécessaire

#### Problème : Les images ne s'affichent pas

**Solution** :

- Vérifiez que le dossier `uploads/` existe à la racine du projet
- Vérifiez les permissions du dossier
- Vérifiez la configuration dans `application.properties` :
  ```properties
  spring.web.resources.static-locations=classpath:/static/,file:uploads/
  ```

## 📁 Structure du Projet

```
my_restaurant/
├── src/main/java/com/example/demo/
│   ├── config/          # Configuration Spring Security
│   ├── controller/      # Contrôleurs MVC
│   ├── model/          # Entités JPA
│   ├── repository/     # Repositories Spring Data
│   ├── service/        # Services métier
│   └── DemoApplication.java
├── src/main/resources/
│   ├── templates/      # Templates Thymeleaf
│   │   ├── admin/      # Pages d'administration
│   │   ├── public/     # Pages publiques
│   │   └── ...
│   ├── static/         # Ressources statiques
│   └── application.properties
└── uploads/           # Dossier d'upload des images
```

## 🎨 Personnalisation

### Couleurs

La couleur dominante configurée dans le setup est utilisée dans tout le site pour :

- Boutons et liens
- En-têtes de sections
- Éléments d'accent

### Images

- **Bannière** : Upload lors de la configuration initiale
- **Photos des plats** : Upload lors de la création/modification des plats
- **Stockage** : Dossier `uploads/` à la racine du projet

## 🔒 Sécurité

- **Authentification** : Spring Security avec BCrypt
- **Validation du mot de passe** : Règles strictes lors de la configuration
- **Protection des routes admin** : Toutes les routes `/admin/**` nécessitent une authentification
- **CSRF** : Désactivé pour simplifier l'utilisation

## 📱 Responsive Design

L'application est entièrement responsive et s'adapte à :

- **Desktop** : Interface complète
- **Tablet** : Adaptation des grilles
- **Mobile** : Navigation optimisée

## 🚀 Fonctionnalités Avancées

### Gestion des Allergènes

Les plats peuvent inclure des informations sur les allergènes selon la réglementation française.

### Système de Reviews

- **Validation côté client** et serveur
- **Affichage des avis récents** sur la page d'accueil
- **Gestion complète** côté administration

### Upload d'Images

- **Validation des types de fichiers**
- **Redimensionnement automatique** (à implémenter)
- **Stockage sécurisé**

## 🔧 Développement

### Ajout de nouvelles fonctionnalités

1. Créer l'entité dans `model/`
2. Créer le repository dans `repository/`
3. Créer le service dans `service/`
4. Créer le contrôleur dans `controller/`
5. Créer les templates dans `templates/`

### Tests

```bash
./gradlew test
```

## 📝 Licence

Ce projet est développé dans le cadre d'un projet académique.

## 🤝 Contribution

Pour contribuer au projet :

1. Fork le repository
2. Créer une branche pour votre fonctionnalité
3. Commiter vos changements
4. Pousser vers la branche
5. Créer une Pull Request

## 📞 Support

Pour toute question ou problème, veuillez ouvrir une issue sur le repository GitHub.

---

**Développé avec ❤️ pour les restaurateurs français**
