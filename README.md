# Pregnancy Vitals Tracker

A modern Android application for tracking pregnancy vitals including blood pressure, heart rate, weight, and baby kicks. Built with Jetpack Compose, Room Database, and WorkManager for periodic reminders.

## Features

- 📊 Track vital signs: Blood Pressure (Systolic/Diastolic), Heart Rate, Weight, Baby Kicks
- 🔔 Periodic reminders to log vitals (configurable: 1 minute for testing, 5 hours for production)
- 💜 Beautiful purple-themed UI that stays consistent in light/dark mode
- 📱 Material 3 Design with Compose
- 💾 Local database storage with Room
- 🔄 Real-time updates with Flow and StateFlow

## Screenshots

The app features a clean, pregnancy-friendly purple theme with:
- White background for easy reading
- Light purple cards for vitals display
- Medium purple header with "Track My Pregnancy" title
- Dark purple footer showing timestamp

## Tech Stack

### Architecture
- **MVVM Pattern** (Model-View-ViewModel)
- **Repository Pattern** for data abstraction
- **Manual Dependency Injection** via AppModule

### Libraries & Technologies
- **Jetpack Compose** - Modern declarative UI
- **Room Database** - Local data persistence
  - Version: 2.6.1
  - KSP for annotation processing
- **WorkManager** - Background task scheduling for reminders
  - Version: 2.9.0
- **Kotlin Coroutines & Flow** - Asynchronous programming
- **Material 3** - Modern Material Design components
- **Kotlin** 2.0.21
- **Gradle** 8.13

## Project Structure

```
com.pregnancy.vitals/
│
├── data/
│   ├── local/
│   │   ├── entity/
│   │   │   └── VitalsEntity.kt           // Room entity with auto-generated ID
│   │   │
│   │   ├── dao/
│   │   │   └── VitalsDao.kt              // Database access object
│   │   │
│   │   └── VitalsDatabase.kt             // Room database instance
│   │
│   └── repository/
│       ├── VitalsRepository.kt           // Repository interface
│       └── VitalsRepositoryImpl.kt       // Repository implementation
│
├── ui/
│   ├── screen/
│   │   ├── VitalsScreen.kt               // Main screen with list
│   │   ├── VitalsViewModel.kt            // ViewModel for business logic
│   │   └── VitalsViewModelFactory.kt     // Factory for ViewModel creation
│   │
│   ├── components/
│   │   ├── VitalsItem.kt                 // Card component for each vital entry
│   │   └── AddVitalsDialog.kt            // Dialog for adding new vitals
│   │
│   ├── theme/
│   │   ├── Color.kt                      // Custom purple color palette
│   │   ├── Theme.kt                      // Material 3 theme configuration
│   │   └── Type.kt                       // Typography definitions
│   │
│   └── utils/
│       └── DateFormatters.kt             // Date formatting utilities
│
├── notification/
│   ├── NotificationConstants.kt          // Channel ID, notification ID constants
│   └── NotificationHelper.kt             // Notification creation & display
│
├── worker/
│   └── ReminderWorker.kt                 // Background worker for reminders
│
├── di/
│   └── AppModule.kt                      // Manual DI: schedules WorkManager
│
└── MainActivity.kt                        // Entry point, DB/Repo/VM setup
```

## Key Components

### 1. Data Layer

#### VitalsEntity
```kotlin
@Entity(tableName = "vitals")
data class VitalsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Auto-generated unique ID
    val sysBp: Int,                                      // Systolic blood pressure
    val diaBp: Int,                                      // Diastolic blood pressure
    val heartRate: Int,                                  // Heart rate (bpm)
    val weightKg: Double,                                // Weight in kg
    val babyKicks: Int,                                  // Number of baby kicks
    val timestamp: Long = System.currentTimeMillis()     // Auto timestamp
)
```

**Important:** Primary key defaults to `0` (not `1`) to allow Room's auto-generation to work properly.

#### VitalsDao
- `insert()` - Inserts new vitals with REPLACE strategy
- `getAll()` - Returns Flow of all vitals ordered by timestamp DESC

#### Repository Pattern
- Interface: `VitalsRepository`
- Implementation: `VitalsRepositoryImpl`
- Provides abstraction between ViewModel and data source

### 2. UI Layer

#### VitalsScreen
- Main screen with TopAppBar showing "Track My Pregnancy"
- LazyColumn displaying list of vitals
- FloatingActionButton to add new vitals
- Opens AddVitalsDialog when FAB is clicked

#### VitalsItem
- Card component displaying:
  - Heart rate with heart icon
  - Blood pressure with monitor icon
  - Weight with scale icon
  - Baby kicks with baby icon
  - Timestamp in purple footer
- Uses Material 3 color scheme

#### VitalsViewModel
- Collects vitals from repository as StateFlow
- `addVitals()` function to insert new entries
- Uses `SharingStarted.WhileSubscribed(5000)` for efficient flow collection

### 3. Notification System

#### NotificationHelper
- Creates notification channel with HIGH importance
- Shows reminder notifications with:
  - Title: "Time to log your vitals!"
  - Body: "Stay on top of your health. Please update your vitals now!"
  - Click action: Opens app with add dialog
  - Vibration and lights enabled
  - Auto-cancel on tap
- Runtime permission check for Android 13+

#### ReminderWorker
- CoroutineWorker that runs in background
- Creates notification channel
- Shows reminder notification
- Error handling with try-catch
- Returns `Result.success()` or `Result.failure()`

#### AppModule
- Schedules WorkManager tasks
- **Test Mode** (`testMode = true`):
  - OneTimeWorkRequest with 1-minute delay
  - For testing notifications quickly
- **Production Mode** (`testMode = false`):
  - PeriodicWorkRequest every 5 hours
  - Uses `ExistingPeriodicWorkPolicy.KEEP` to avoid resetting timer
- Constraints: No network required

### 4. Theme

#### Custom Purple Color Palette
```kotlin
val LightPurple = Color(0xFFE8D5F2)      // Light lavender for cards
val MediumPurple = Color(0xFF9B7EBD)     // Medium purple for header
val DarkPurple = Color(0xFF7B5FA1)       // Dark purple for footer/text
val BackgroundWhite = Color(0xFFFFFFFF)  // Pure white background
```

#### Theme Configuration
- **Dynamic colors disabled** - Uses custom purple theme always
- **Same theme for light/dark mode** - Consistent appearance
- Material 3 color scheme with custom colors

## Setup & Installation

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 33 or higher
- Kotlin 2.0.21
- Gradle 8.13

### Build & Run

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Run on emulator or physical device (Android 13+)

### Gradle Dependencies

```kotlin
// Room Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")

// WorkManager
implementation("androidx.work:work-runtime-ktx:2.9.0")

// Compose & Material 3
implementation(platform("androidx.compose:compose-bom:2024.09.00"))
implementation("androidx.compose.material3:material3")
implementation("androidx.compose.material:material-icons-extended")
```

### Permissions

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

## Configuration

### Switching Between Test and Production Mode

In `MainActivity.kt`:

```kotlin
// Test mode: 1-minute reminders
AppModule.scheduleVitalsReminder(this, testMode = true)

// Production mode: 5-hours reminders
AppModule.scheduleVitalsReminder(this, testMode = false)
```

### Customizing Notification Interval

In `AppModule.kt`:

```kotlin
// Change the interval (minimum 5 hours for PeriodicWorkRequest)
val request = PeriodicWorkRequestBuilder<ReminderWorker>(5, TimeUnit.HOURS)
```

## Database

- **Name:** `vitals.db`
- **Version:** 1
- **Tables:** `vitals`
- **Location:** App's internal storage

### Database Schema

| Column | Type | Description |
|--------|------|-------------|
| id | Int | Primary key (auto-generated) |
| sysBp | Int | Systolic blood pressure |
| diaBp | Int | Diastolic blood pressure |
| heartRate | Int | Heart rate in bpm |
| weightKg | Double | Weight in kilograms |
| babyKicks | Int | Number of baby kicks |
| timestamp | Long | Unix timestamp (milliseconds) |

## Known Issues & Solutions

### Issue: Only one item shows in list
**Cause:** Primary key defaulting to 1 instead of 0  
**Solution:** Fixed - now defaults to 0 for proper auto-generation

### Issue: Notifications not showing
**Causes:**
1. Permission not granted on Android 13+
2. Notification channel not created
3. App in battery optimization


### Application Download Link:
https://www.upload-apk.com/hZniycWKFwCHet3