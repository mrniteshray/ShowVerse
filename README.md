<div align="center">

# 🎬 ShowVerse

**A Modern Android App for Discovering Movies & TV Shows**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.21-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-2025.10.01-green.svg?style=flat&logo=android)](https://developer.android.com/jetpack/compose)

<p align="center">
  <img src="https://img.shields.io/badge/Made%20with-❤️-red.svg?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Architecture-Clean%20MVVM-blue.svg?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/DI-Koin-green.svg?style=for-the-badge"/>
</p>

</div>

---

## 📖 Overview

**ShowVerse** is a beautifully crafted Android application that allows users to explore and discover movies and TV shows. Built with modern Android development practices, it showcases the latest technologies and architectural patterns in Android app development.

### ✨ Key Features

- 🎥 **Browse Movies** - Discover a curated list of popular movies
- 📺 **Explore TV Shows** - Browse through trending TV series
- 🔍 **Detailed Information** - View comprehensive details including ratings, genres, runtime, and plot overviews
- 🎨 **Modern UI** - Built entirely with Jetpack Compose

---

## 🏗️ Architecture

This project follows **Clean Architecture** principles with **MVVM** pattern, ensuring separation of concerns and testability.

```
ShowVerse/
├── core/               # Core utilities and constants
│   ├── di/            # Dependency Injection modules (Koin)
│   └── util/          # Utilities and helper classes
├── data/              # Data layer
│   ├── model/         # Data models
│   ├── remote/        # API service definitions
│   └── repository/    # Repository implementations
├── domain/            # Domain layer (Business Logic)
│   ├── repository/    # Repository interfaces
│   └── usecase/       # Use cases
└── presentation/      # Presentation layer
    ├── home/          # Home screen with Movies/TV Shows tabs
    ├── detail/        # Detail screen for content
    ├── components/    # Reusable UI components
    └── navigation/    # Navigation setup
```

### 🎯 Architecture Highlights

- **Clean Architecture**: Separation of concerns with clear layer boundaries
- **MVVM Pattern**: UI logic separated from business logic
- **Repository Pattern**: Abstraction of data sources
- **Use Cases**: Encapsulated business logic
- **Dependency Injection**: Using Koin for loose coupling
- **Reactive Programming**: RxJava3 for asynchronous operations

---

## 🛠️ Tech Stack

### **Core Technologies**
- **[Kotlin](https://kotlinlang.org/)** - Modern, concise programming language
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Modern declarative UI toolkit
- **[Material 3](https://m3.material.io/)** - Latest Material Design components

### **Architecture Components**
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)** - UI state management
- **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation)** - In-app navigation
- **[Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)** - Lifecycle-aware components

### **Dependency Injection**
- **[Koin](https://insert-koin.io/)** - Lightweight DI framework for Kotlin

### **Networking**
- **[Retrofit](https://square.github.io/retrofit/)** - Type-safe HTTP client
- **[OkHttp](https://square.github.io/okhttp/)** - HTTP client with logging interceptor
- **[Gson](https://github.com/google/gson)** - JSON serialization/deserialization

### **Reactive Programming**
- **[RxJava 3](https://github.com/ReactiveX/RxJava)** - Reactive extensions for JVM
- **[RxAndroid](https://github.com/ReactiveX/RxAndroid)** - Android-specific bindings
- **[RxKotlin](https://github.com/ReactiveX/RxKotlin)** - Kotlin extensions for RxJava

### **UI Enhancements**
- **[Coil](https://coil-kt.github.io/coil/)** - Image loading library for Compose
- **[Shimmer](https://github.com/valentinilk/compose-shimmer)** - Shimmer loading effect
- **[Accompanist](https://google.github.io/accompanist/)** - Compose utilities (FlowLayout)

### **API**
- **[Watchmode API](https://api.watchmode.com/)** - Movie and TV show data provider

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: Version 11 or higher
- **Gradle**: 8.13 (via wrapper)
- **Min SDK**: 28 (Android 9.0 Pie)
- **Target SDK**: 36

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/mrniteshray/ShowVerse.git
   cd ShowVerse
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Get API Key**
   - Visit [Watchmode API](https://api.watchmode.com/)
   - Sign up for a free API key
   - Replace the API key in `app/src/main/java/xcom/niteshray/xapps/showverse/core/util/Constants.kt`
   ```kotlin
   const val API_KEY = "YOUR_API_KEY_HERE"
   ```

4. **Build and Run**
   - Sync Gradle files
   - Run the app on an emulator or physical device

---

<div align="center">

**Made with ❤️ using Kotlin & Jetpack Compose**

</div>
