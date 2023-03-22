package com.deepak.movielisting.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deepak.movielisting.data.local.dao.MoviesDao
import com.deepak.movielisting.ui.models.SearchResultsItem

/**
 * Created by Deepak Kumar Verma on 10-01-2021.
 * Company Shantiinfotech.
 */

@Database(entities = [SearchResultsItem::class], version = 4, exportSchema = false)
abstract class  MovieDatabase : RoomDatabase(){
    abstract fun movieDao(): MoviesDao
}