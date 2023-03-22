package com.deepak.movielisting.data.remote

import com.deepak.movielisting.ui.models.SearchModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Deepak Kumar Verma on 10-01-2021.
 * Company Shantiinfotech.
 */
interface UrlServcies {
    @GET("movie/popular?language=en-US&region=US")
    fun getmovies(
        @Query("page") page: Long
    ): Observable<SearchModel>



    @GET("/3/search/movie")
    fun searchMovies(
            @Query("query") query: String,
            @Query("page") page: String
    ): Observable<SearchModel>

}