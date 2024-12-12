package com.ns.fakex.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.ns.fakex.data.database.AppDatabase
import com.ns.fakex.data.database.TweetsDao
import com.ns.fakex.utils.Constants

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
    ): AppDatabase {
        val buildRoom = Room
            .databaseBuilder(application, AppDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
        return buildRoom.build()
    }

    @Provides
    @Singleton
    fun provideTweetsDao(localDatabase: AppDatabase): TweetsDao {
        return localDatabase.tweetsDao()
    }
}