# Travel Souvenir App 📸🌍

This Android app is a **location-based travel tracker** that allows users to capture and save trip memories in city-specific albums, fetch additional information about locations from Wikipedia, and receive push notifications with deep linking to navigate seamlessly within the app.

---

## Table of Contents
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Dependencies](#dependencies)
- [Features](#features)
- [Screenshots](#screenshots)
  
---

## Getting Started

To get your project up and running, follow these steps:

### Prerequisites

Make sure you have the following installed on your machine:

- [Android Studio](https://developer.android.com/studio) (latest version recommended)
- Java Development Kit (JDK) 11 or higher

### Clone the Repository

First, clone the repository to your local machine using:

```bash
git clone https://github.com/mistedsait/travelsouvenir.git
```
### Open the Project

1. Open Android Studio.
2. Select "Open an existing Android Studio project."
3. Navigate to the cloned repository and open it.

### Configure Firebase

1. **Place `google-services.json` in the `app/` directory**: 
   - This file enables Firebase services. You can obtain it by creating a Firebase project and adding an Android app in the Firebase console.

### Set Up Dependencies

Ensure you have all the required dependencies listed in your `build.gradle` files. Sync your Gradle files after any modifications.

### Run the App

1. Connect your Android device or start an emulator.
2. Click on the Run button in Android Studio.
3. Select your device/emulator and wait for the app to build and launch.

### Dependencies

Make sure your project includes the following dependencies in your `build.gradle` file:

- **Firebase Cloud Messaging**: For push notifications.
- **Retrofit**: For API calls to Wikipedia for city information.
- **Jetpack Compose**: UI toolkit for creating UI elements.
- **Room Database**: Data persistence solution.
- **Hilt**: Dependency injection framework for managing app components.
- **Accompanist Pager**: For smooth screen transitions and animations.

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

### Future Plans

1. **Improving the UI**: 
   - Enhance the user interface with more visually appealing designs and animations to improve user experience.

2. **Integrating Custom API**:
   - Develop and integrate a custom API to provide additional features and functionalities tailored to user needs.

3. **User Authentication**:
   - Implement user authentication to allow users to create accounts and securely log in.

4. **Offline Mode**:
   - Enhance offline capabilities to allow users to access certain features without an internet connection, improving usability during travel.

5. **Social Sharing**:
   - Allow users to share their travel experiences and photos on social media platforms directly from the app.

6. **Gamification Elements**:
   - Add gamification elements, such as challenges and leaderboards, to engage users and encourage participation.

7. **Location-Based Recommendations**:
    - Provide users with recommendations for nearby attractions and places of interest based on their current location.






