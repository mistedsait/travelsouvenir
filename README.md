# Travel Companion App 📸🌍

This Android app is a **location-based travel tracker** that allows users to capture and save trip memories in city-specific albums, fetch additional information about locations from Wikipedia, and receive push notifications with deep linking to navigate seamlessly within the app.

## Table of Contents
- [Features](#features)
- [Screenshots](#screenshots)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Dependencies](#dependencies)

---

## Features

### 1. Location-Based Travel Tracking and Gallery
   - **Camera Integration**: Users can capture photos during their travels.
   - **Location Permission**: Requests location access to determine the city or place where each photo is taken.
   - **Reverse Geocoding**: Uses GPS data to identify city names, organizing photos into city-specific galleries.
   - **City-Specific Albums**: Automatically saves photos into albums named after each city.

### 2. Navigation and Deep Linking
   - **Multi-Screen Layout**: Includes a Landing Page, MyPlaceScreen, and GalleryScreen for easy navigation.
   - **Navigation with NavHost**: Manages screen transitions and navigation with NavHost.
   - **Deep Linking with Firebase**: Push notifications use deep linking to open the GalleryScreen for a specific place.
   - **Bottom Navigation Bar**: Icons for Home, Camera, Bookmarks, and Profile for quick access.

### 3. Database and Data Persistence
   - **Room Database**: Saves places locally with city names and photo metadata, making the app functional offline.
   - **ViewModels**: Separates data with `PlacesViewModel` and `GalleryViewModel` for clear lifecycle management.
   - **Repository Pattern**: Provides a `PlaceRepository` interface and `PlaceRepositoryImpl` for structured data access.

### 4. Wikipedia API Integration
   - **City Information Fetching**: Retrieves city descriptions and images from Wikipedia for each detected location.
   - **Dynamic Content Loading**: Displays city-specific data dynamically based on user location.

### 5. Push Notifications
   - **Firebase Cloud Messaging (FCM)**: Sends notifications to engage users.
   - **Deep Linking in Notifications**: Notifications open specific screens, such as GalleryScreen, based on the payload data.
   - **Payload Handling**: Manages in-app navigation based on data sent from Firebase, ensuring the user reaches relevant screens.

### 6. Animated UI Transitions
   - **Screen Transitions**: Smooth animations, including slide and fade transitions, improve navigation experience.
   - **Animated Loading Indicators**: Circular progress indicators show data is loading.

### 7. Utility and Helper Classes
   - **LocationHelper**: Handles location services, permissions, and reverse geocoding.
   - **PhotoHelper**: Manages camera functionality, saving images with metadata.

### 8. Permissions Management
   - **Camera and Location Permissions**: Dual permission requests allow for location-based photo management.
   - **Error Handling for Permissions**: Provides guidance when permissions aren’t granted.

### 9. Image Handling
   - **FileProvider for Secure Image Access**: Enables secure image sharing within the app.
   - **Temporary Image Storage**: Saves captured photos in a temporary app directory.

### 10. User Interface Components
   - **Custom Top and Bottom App Bars**: Adds visual appeal with a branded top bar and bottom navigation.
   - **Custom Icons and Images**: Enhances the UI with unique icons and imagery for easy navigation.

---

## Screenshots

<img src="https://github.com/user-attachments/assets/bc5e15d9-464b-4fa5-89e1-1fb32a89cb7e" alt="Screenshot 1" width="250" height="550"/>
<img src="https://github.com/user-attachments/assets/e2264245-998d-4eb4-b2c3-f563d1cb8209" alt="Screenshot 2" width="250" height="550"/>
<img src="https://github.com/user-attachments/assets/1d9ff69d-2e83-44b6-83a6-f996fd7c3bea" alt="Screenshot 3" width="250" height="550"/>


---

## Dependencies

- **Firebase Cloud Messaging**: For push notifications.
- **Retrofit**: For making API calls to Wikipedia to retrieve city information.
- **Jetpack Compose**: UI toolkit for creating user interface elements.
- **Room Database**: Ensures data persistence for saved locations and photos.
- **Hilt**: Dependency injection framework for managing and injecting app components.
- **Accompanist Pager**: Provides smooth animations and screen transitions.

---


