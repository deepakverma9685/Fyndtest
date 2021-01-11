package com.deepak.fyndtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deepak.fyndtest.data.local.dao.MoviesDao
import com.deepak.fyndtest.ui.models.SearchResultsItem

/**
 * Created by Deepak Kumar Verma on 10-01-2021.
 * Company Shantiinfotech.
 */

@Database(entities = [SearchResultsItem::class], version = 4, exportSchema = false)
abstract class  FyndDatabase : RoomDatabase(){
    abstract fun movieDao(): MoviesDao
}