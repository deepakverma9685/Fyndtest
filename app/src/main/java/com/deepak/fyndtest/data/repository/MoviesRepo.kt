package com.deepak.fyndtest.data.repository

import android.util.Log
import com.deepak.fyndtest.data.local.dao.MoviesDao
import com.deepak.fyndtest.data.remote.NetworkBoundResource
import com.deepak.fyndtest.data.remote.Resource
import com.deepak.fyndtest.data.remote.UrlServcies
import com.deepak.fyndtest.ui.models.SearchModel
import com.deepak.fyndtest.ui.models.SearchResultsItem
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import java.util.*
import javax.inject.Singleton
import kotlin.collections.ArrayList

/**
 * Created by Deepak Kumar Verma on 10-01-2021.
 * Company Shantiinfotech.
 */

@Singleton
class MoviesRepo(private val urlServcies: UrlServcies, private val moviesDao: MoviesDao) {
    private val TAG = "MoviesRepo"

    fun loadmovies(page:Long) : Observable<Resource<List<SearchResultsItem>>>{
        return object : NetworkBoundResource<List<SearchResultsItem>,SearchModel>(){
            override fun saveCallResult(item: SearchModel) {
                val movietiem = ArrayList<SearchResultsItem>()
                for (searchitem in item.results!!) {
                    searchitem?.page = item.page!!
                    searchitem?.totalPages = item.totalPages!!
                    if (searchitem != null) {
                        movietiem.add(searchitem)
                    }
                }
                moviesDao.insertMovies(movietiem as List<SearchResultsItem>)
            }

            override fun shouldFetch(): Boolean {
                return true

           //     return moviesDao.getMovies().size == 0 ?: true
            }

            override fun loadFromDb(): Flowable<List<SearchResultsItem>> {
                val movieEntities = moviesDao.getMovies()
                return Flowable.just(movieEntities)
            }

            override fun createCall(): Observable<Resource<SearchModel>> {
                return urlServcies.getmovies(page).flatMap {
                    Observable.just(
                        if (it == null) Resource.error("error", SearchModel(1, 0, emptyList(), 1))
                        else Resource.success(it)
                    )
                }
            }

        }.getAsObservable()
    }

    fun searchMovies(query:String) : Observable<Resource<List<SearchResultsItem>>>{
        return object : NetworkBoundResource<List<SearchResultsItem>,SearchModel>(){
            override fun saveCallResult(item: SearchModel) {
                moviesDao.insertMovies(item.results as List<SearchResultsItem>)
            }

            override fun shouldFetch(): Boolean {
               return true
            }

            override fun loadFromDb(): Flowable<List<SearchResultsItem>> {
                val movieEntities = moviesDao.loadHamsters(query)
                return Flowable.just(movieEntities)
            }

            override fun createCall(): Observable<Resource<SearchModel>> {
              return urlServcies.searchMovies(query,"1").flatMap {
                  Observable.just(
                      if (it == null) Resource.error("error", SearchModel(1, 0, emptyList(), 1))
                      else Resource.success(it)
                  )
              }
            }

        }.getAsObservable()
    }
}

