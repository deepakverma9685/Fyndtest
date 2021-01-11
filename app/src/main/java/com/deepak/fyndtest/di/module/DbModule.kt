package com.deepak.fyndtest.di.module

import android.app.Application
import androidx.room.Room
import com.deepak.fyndtest.data.local.FyndDatabase
import com.deepak.fyndtest.data.local.dao.MoviesDao
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): FyndDatabase {
        return Room.databaseBuilder(application, FyndDatabase::class.java, "Entertainment.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    internal fun provideMovieDao(appDatabase: FyndDatabase): MoviesDao {
        return appDatabase.movieDao()
    }


}
