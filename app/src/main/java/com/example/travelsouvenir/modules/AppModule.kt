package com.example.travelsouvenir.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.travelsouvenir.data.AppDatabase
import com.example.travelsouvenir.data.PlaceDao
import com.example.travelsouvenir.data.api.RetrofitInstance
import com.example.travelsouvenir.data.api.WikipediaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide the Room database
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database" // Change this name as necessary
        )   .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun providePlaceDao(appDatabase: AppDatabase): PlaceDao {
        return appDatabase.placeDao()
    }


    @Provides
    @Singleton
    fun provideWikipediaApiService(): WikipediaApiService {
        return RetrofitInstance.api
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}
