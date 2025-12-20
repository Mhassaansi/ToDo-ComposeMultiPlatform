📔 KMP Todo Multiplatform
A production-ready Todo & Notes application built with Kotlin Multiplatform (KMP). This project demonstrates a unified codebase for Android and Desktop (JVM) using modern declarative UI and reactive data persistence.

✨ Features
Full CRUD Operations: Create, Read, Update, and Delete notes.

Real-time Updates: Reactive UI that updates instantly when the database changes using Kotlin Flows.

Cross-Platform UI: 100% shared UI code between Android and Desktop using Compose Multiplatform.

Local Persistence: Data is stored locally on each platform using the Room KMP library.

Dependency Injection: Clean architecture powered by Koin for managing platform-specific dependencies.

🛠 Tech Stack
UI: Compose Multiplatform

Database: Room KMP (SQLite)

DI Framework: Koin

Async: Kotlin Coroutines & Flow

Architecture: MVVM (Model-View-ViewModel) with Clean Architecture layers.

📂 Project Structure
Plaintext

├── composeApp/
│   ├── commonMain/           # Shared UI, ViewModels, and Room Database definition
│   │   ├── kotlin/
│   │   │   ├── data/         # Room Entities, DAOs, and Database
│   │   │   ├── di/           # Common Koin Modules
│   │   │   ├── ui/           # Shared Composables (NoteList, EditNote)
│   │   │   └── viewmodel/    # Shared Business Logic
│   ├── androidMain/          # Android-specific Room/Koin initialization
│   └── desktopMain/          # Desktop-specific Room/Koin initialization
└── gradle/                   # Version catalog (libs.versions.toml)


⚙️ Setup & Configuration
1. Database (Room)
The app uses the new Room Multiplatform runtime.

Android: Database is created using Context.getDatabasePath().

Desktop: Database is stored in the local application data directory using java.io.File.

2. Dependency Injection (Koin)
The project utilizes expect/actual modules to provide platform-specific database builders:

Kotlin

// commonMain
expect val platformModule: Module

// Initialization in commonMain
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
    }
}
🚀 How to Run
Prerequisites
Android Studio Ladybug or IntelliJ IDEA 2024.2+

JDK 17+

Kotlin Multiplatform plugin installed.

Running on Android
Select the composeApp run configuration.

Select your Android Emulator or physical device.

Click Run.

Running on Desktop (Windows/macOS/Linux)
Open the Gradle tool window.

Navigate to composeApp > Tasks > compose desktop > run.

Or use the terminal:

Bash

./gradlew :composeApp:run
📝 Usage Guide
Add Note: Click the Floating Action Button (FAB) to open the editor.

Edit: Tap/Click on any note to modify its content.

Delete: Long-press (Android) or Right-click/Delete Icon (Desktop) to remove a note.

Save: Changes are auto-persisted to the Room database.
