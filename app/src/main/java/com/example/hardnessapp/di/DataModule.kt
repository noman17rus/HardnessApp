package com.example.hardnessapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hardnessapp.data.SampleDao
import com.example.hardnessapp.data.SampleDb
import com.example.hardnessapp.data.SampleRepository
import com.example.hardnessapp.data.SampleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): SampleDb {
        return Room.databaseBuilder(context, SampleDb::class.java, "Samples").build()
    }

    @Provides
    @Singleton
    fun provideDao(database: SampleDb): SampleDao {
        return database.getDao()
    }

    @Provides
    @Singleton
    fun provideRepository(dao: SampleDao): SampleRepository {
        return SampleRepositoryImpl(dao = dao)
    }

}