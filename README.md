# Quran App

A comprehensive Quran application for Android, built using Jetpack Compose, Hilt, Room, and Retrofit. This app allows users to browse Surahs, view Ayahs with their Arabic text and English translations, toggle between light and dark themes, and mark Ayahs as favorites.

## Features

- **Surah and Ayah Listings**: Browse through all the Surahs of the Quran and view their respective Ayahs.
- **Arabic and English Translation**: Displays the Arabic text along with an English translation for each Ayah.
- **Favorite Ayahs**: Mark Ayahs as favorites for quick access.
- **Theme Toggling**: Easily switch between light and dark themes within the app.
- **Responsive and Optimized**: Uses Jetpack Compose for a responsive and modern UI experience.

## Screenshots

![WhatsApp Image 2024-11-12 at 11 06 43 PM](https://github.com/user-attachments/assets/d5851277-0393-480e-bfee-3f553b22bf0f)

![WhatsApp Image 2024-11-12 at 11 06 44 PM](https://github.com/user-attachments/assets/667bc70d-0b66-4d60-8f19-2d1b5b5703b4)

![WhatsApp Image 2024-11-12 at 11 06 44 PM (1)](https://github.com/user-attachments/assets/ad990fea-73b3-4226-910e-da776f9bc620)

![WhatsApp Image 2024-11-12 at 11 06 45 PM (1)](https://github.com/user-attachments/assets/ff317ae0-5178-4993-8671-aafe824c2b8a)

![WhatsApp Image 2024-11-12 at 11 06 45 PM](https://github.com/user-attachments/assets/4d12a9f2-81eb-468b-a26a-43d572466b7c)

## Installation

You can download the latest version of the app from the link below:

[Download APK](https://github.com/LaibaIstkar/Quran/raw/main/apk/app-debug.apk)

## Architecture

The app follows the MVVM (Model-View-ViewModel) architecture pattern and makes use of several Jetpack libraries.

### Key Components

- **Jetpack Compose**: Used for building the app's UI.
- **Hilt**: For dependency injection.
- **Room**: To store Surahs, Ayahs, and favorite Ayahs locally.
- **Retrofit**: To fetch data from the Quran API.

### Data Flow

1. **Repository**: `QuranRepository` handles data fetching from the remote API and local database.
2. **ViewModel**: `QuranViewModel` fetches and provides data to the UI, following the lifecycle of `MainActivity` and other composable functions.
3. **API**: `QuranApiService` communicates with the Quran API to fetch Surah and Ayah data.
4. **Database**: `QuranDatabase` stores Surahs, Ayahs, and favorite Ayahs using Room.

## Code Structure

- **MainActivity**: The entry point of the app, responsible for theme toggling and initializing the main composable UI.
- **QuranApp Composable**: Main navigation composable that sets up the navigation graph.
- **SurahListScreen**: Lists all Surahs.
- **SurahDetailScreen**: Displays all Ayahs within a selected Surah.
- **FavoriteAyahsPage**: Shows a list of favorite Ayahs.
- **QuranDao**: Provides methods to insert and fetch data from the Room database.
- **QuranRepository**: Manages data sources (API and local database).
- **QuranApiService**: Contains the Retrofit interface for the Quran API.

## Libraries Used

- **Jetpack Compose**: For building a declarative UI.
- **Hilt**: For dependency injection.
- **Room**: For local storage of Surahs and Ayahs.
- **Retrofit**: For networking and API calls.
- **LiveData**: To observe and react to data changes.
- **Coroutines**: For asynchronous programming.

