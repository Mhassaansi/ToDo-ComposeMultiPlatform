# Notes App - Compose Multiplatform

A modern, cross-platform note-taking application built with Kotlin Multiplatform and Jetpack Compose, supporting both Android and Desktop platforms.

## 🚀 Features

- ✅ Create, edit, and delete notes
- 🔍 Search and filter notes by title
- 💾 Local persistence using Room KMP
- 🎨 Material 3 design with responsive UI
- 📱 Supports Android and Desktop (Windows, macOS, Linux)
- 🏗️ Clean MVVM architecture
- 💉 Dependency injection with Koin

📱 Screenshots
<div align="center">
  <img src="https://github.com/Mhassaansi/ToDo-ComposeMultiPlatform/blob/master/WhatsApp%20Image%202025-12-20%20at%2013.18.40%20(1).jpeg" alt="Notes List Screen" width="250"/>
  <img src="https://github.com/Mhassaansi/ToDo-ComposeMultiPlatform/blob/master/WhatsApp%20Image%202025-12-20%20at%2013.18.40.jpeg" alt="Create Note Screen" width="250"/>
  <img src="https://github.com/Mhassaansi/ToDo-ComposeMultiPlatform/blob/master/WhatsApp%20Image%202025-12-20%20at%2013.18.41.jpeg" alt="Edit Note Screen" width="250"/>
</div>

## 🛠️ Tech Stack

### Core Technologies
- **Kotlin Multiplatform**: Share business logic across platforms
- **Jetpack Compose Multiplatform**: Unified UI framework
- **Room KMP**: Cross-platform local database
- **Koin**: Dependency injection framework
- **Kotlin Coroutines & Flow**: Asynchronous programming

### Architecture
- **MVVM Pattern**: Clear separation of concerns
- **Repository Pattern**: Data layer abstraction
- **StateFlow**: Reactive state management
- **expect/actual**: Platform-specific implementations

## 📋 Requirements

- **JDK**: 17 or higher
- **Android Studio**: Ladybug (2024.2.1) or newer
- **Kotlin**: 2.1.0+
- **Gradle**: 8.5+
- **Android SDK**: Min API 24, Target API 35
- **Desktop**: JVM 17+



## 🔧 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/notes-app-kmp.git
cd notes-app-kmp
```

### 2. Configure Dependencies

The project uses Gradle version catalog (`libs.versions.toml`):

```toml
[versions]
kotlin = "2.1.0"
compose = "1.7.1"
agp = "8.7.3"
androidx-activityCompose = "1.9.3"
room = "2.7.0-alpha12"
koin = "4.0.0"
ksp = "2.1.0-1.0.29"

[libraries]
androidx-activity-compose = { module = "androidx.activity:activity-compose", version.ref = "androidx-activityCompose" }
room-runtime = { module = "androidx.room:room-runtime", version.ref = "room" }
room-compiler = { module = "androidx.room:room-compiler", version.ref = "room" }
room-ktx = { module = "androidx.room:room-ktx", version.ref = "room" }
sqlite-bundled = { module = "androidx.sqlite:sqlite-bundled", version = "2.5.0-alpha12" }
koin-core = { module = "io.insert-koin:koin-core", version.ref = "koin" }
koin-compose = { module = "io.insert-koin:koin-compose", version.ref = "koin" }
koin-compose-viewmodel = { module = "io.insert-koin:koin-compose-viewmodel", version.ref = "koin" }

[plugins]
androidApplication = { id = "com.android.application", version.ref = "agp" }
kotlinMultiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
composeMultiplatform = { id = "org.jetbrains.compose", version.ref = "compose" }
composeCompiler = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
room = { id = "androidx.room", version.ref = "room" }
```

### 3. Build the Project

```bash
./gradlew build
```

### 4. Run on Android

```bash
./gradlew :composeApp:installDebug
```

Or open the project in Android Studio and run the Android configuration.

### 5. Run on Desktop

```bash
./gradlew :composeApp:run
```

## 🏗️ Architecture Overview

### Data Layer

**Room Database**
- `NoteEntity`: Data model with id, title, content, timestamp
- `NoteDao`: Database access with CRUD operations
- `NotesDatabase`: Room database configuration

**Platform-Specific Database Builders**
- Android: Uses application context for database location
- Desktop: Uses user home directory for database file

### Domain Layer

**Repository**
- `NotesRepository`: Abstraction layer between ViewModel and database
- Handles data operations and transformations

### Presentation Layer

**ViewModel**
- `NotesViewModel`: Manages UI state using StateFlow
- Exposes notes list, search functionality, and CRUD operations
- Platform-agnostic, lives in `commonMain`

**UI Components**
- `NotesListScreen`: Displays all notes in a grid/list
- `NoteEditScreen`: Create or edit individual notes
- Material 3 theming with adaptive layouts

### Dependency Injection

**Koin Modules**
- Database module: Provides Room database instance
- Repository module: Provides NotesRepository
- ViewModel module: Provides NotesViewModel


## 🔑 Key Implementation Details

### expect/actual Pattern

```kotlin
// commonMain
expect class DatabaseBuilder {
    fun build(): NotesDatabase
}

// androidMain
actual class DatabaseBuilder(private val context: Context) {
    actual fun build(): NotesDatabase {
        val dbFile = context.getDatabasePath("notes.db")
        return Room.databaseBuilder<NotesDatabase>(
            context = context.applicationContext,
            name = dbFile.absolutePath
        ).build()
    }
}

// desktopMain
actual class DatabaseBuilder {
    actual fun build(): NotesDatabase {
        val dbFile = File(System.getProperty("user.home"), "notes.db")
        return Room.databaseBuilder<NotesDatabase>(
            name = dbFile.absolutePath
        ).build()
    }
}
```


```bash
# Common tests
./gradlew :composeApp:cleanTestDebugUnitTest :composeApp:testDebugUnitTest

# Android tests
./gradlew :composeApp:connectedAndroidTest

# Desktop tests
./gradlew :composeApp:desktopTest
```
### Android APK

```bash
./gradlew :composeApp:assembleRelease
```

Output: `composeApp/build/outputs/apk/release/composeApp-release.apk`

### Desktop Application

```bash
./gradlew :composeApp:packageDistributionForCurrentOS
```

Output: `composeApp/build/compose/binaries/main/`

## 🐛 Troubleshooting

### Common Issues

**Issue: Room compilation errors**
```bash
# Clean and rebuild
./gradlew clean
./gradlew build --refresh-dependencies
```

**Issue: Koin injection failures**
- Ensure `startKoin` is called before accessing ViewModels
- Check module definitions are correct

**Issue: Desktop database path errors**
- Verify write permissions in user home directory
- Check database file creation in logs

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request


## 📞 Support

For questions or issues:
- Open an issue on GitHub
- Email: hsoftz1996@gmail.com


**Built with ❤️ using Kotlin Multiplatform and Compose**
