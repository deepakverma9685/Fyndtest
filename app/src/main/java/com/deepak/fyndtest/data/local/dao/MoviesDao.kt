package com.deepak.fyndtest.data.local.dao

import androidx.room.*
import com.deepak.fyndtest.ui.models.SearchResultsItem
import io.reactivex.Flowable

/**
 * Created by Deepak Kumar Verma on 10-01-2021.
 * Company Shantiinfotech.
 */

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<SearchResultsItem>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: SearchResultsItem): Long

    @Query("SELECT * FROM SearchResultsItem WHERE title LIKE '%' || :search || '%'")
    fun loadHamsters(search: String?): List<SearchResultsItem>

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateMovie(movie: ResultsItem): Int
//
//    @Query("SELECT * FROM `ResultsItem` where id = :id")
//    fun getMovieById(id: Long?): ResultsItem
//
//    @Query("SELECT * FROM `ResultsItem` where id = :id")
//    fun getMovieDetailById(id: Long?): Flowable<ResultsItem>

//    @Query("SELECT * FROM `SearchResultsItem` where page = :page")
//    fun getMoviesByPage(page: Long): List<SearchResultsItem>

    @Query("SELECT * FROM `SearchResultsItem`")
    fun getMovies(): List<SearchResultsItem>
}