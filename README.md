    # VillageEvo2

    VillageEvo2 is an Android simulation game built with **Kotlin** and **Jetpack Compose**. The game focuses on village evolution where players manage resources, assign NPCs to various tasks, and evolve their settlement.

    ## Architecture

    The project follows the **MVVM (Model-View-ViewModel)** pattern with Clean Architecture principles.

    - **Domain**: Contains the core business logic and entities (e.g., `NpcEntity`, `MapDataEntity`).
    - **Data (DB)**: Handles data persistence using **Room Database**.
    - **UI**: Built completely with **Jetpack Compose**.
    - **ViewModel**: Manages UI state and business logic.

    ### Key Components

    #### 1. Queries (Data Layer)
    The database interactions are handled via DAOs (Data Access Objects) in the `db` package.

    *   **`NpcDao.kt`**: Manages NPC data.
        *   `getNpcWithOrderAndFilter`: A complex query that retrieves NPCs with their assigned abilities, supporting filtering (e.g., filter by `infantry`, `archer`) and sorting.
        *   `insertNpcAssign`: Handles assigning NPCs to specific map locations.
    *   **`MapUserDao.kt`**: Manages map and resource data.
        *   `runTurnTransaction`: A specialized transaction that updates resource counts and map data validation in a single atomic operation, essential for the "Turn" based gameplay.
        *   `getPotentialSource`: Calculates the potential resource yield of map tiles based on the assigned NPCs and their skill levels.

    #### 2. Pages (UI Layer)
    The UI is structured into Screens and Components.

    *   **`NavGraph.kt`**: The central navigation hub.
        *   **Home Route**: `HomeScreen` - The main dashboard.
        *   **Map Route**: `MapScreen` - Displays the game map where players can interact with tiles and view details.
    *   **`ui/screens`**: Contains the top-level composables for each screen.
    *   **`ui/components`**: Contains reusable UI elements like `IndicatorMap` and `BuildArea`.

    #### 3. ViewModels
    ViewModels act as the bridge between the Repository/Domain layer and the UI.

    *   **`GameViewModel`**: Manages the overall game state and session.
    *   **`MapViewModel`**: 
        *   Handles map logic, including loading user map data (`dataMapUserById`).
        *   Executes the turn processing logic (`turnProcess`), which calculates resource consumption and production based on NPC assignments.
    *   **`NpcViewModel`**: 
        *   Manages NPC lists, abilities, and assignments.
        *   `saveNpcFirst`: Initializes dummy NPC data (currently hardcoded).
        *   `loadNpcAbilityList`: Loads NPCs with pagination support.

    ## Getting Started

    1.  **Prerequisites**: Android Studio Ladybug or newer, JDK 17+.
    2.  **Build**: Sync Gradle project and run on an emulator or physical device.
    3.  **Database**: The app uses a local SQLite database accessed via Room.

    ## Project Structure

    ```
    com.example.villageevo
    ├── db/             # Room DAOs and Database configuration
    ├── domain/         # Data classes and Entities
    ├── repository/     # Repositories for data access
    ├── ui/             # Jetpack Compose Screens and Components
    ├── viewmodel/      # Architecture components
    ├── MainActivity.kt # App entry point
    └── NavGraph.kt     # Navigation definitions
    ```
