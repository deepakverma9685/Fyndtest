package com.deepak.movielisting.di.module

import android.app.Application
import androidx.room.Room
import com.deepak.movielisting.data.local.MovieDatabase
import com.deepak.movielisting.data.local.dao.MoviesDao
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): MovieDatabase {
        return Room.databaseBuilder(application, MovieDatabase::class.java, "Entertainment.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    internal fun provideMovieDao(appDatabase: MovieDatabase): MoviesDao {
        return appDatabase.movieDao()
    }


}
