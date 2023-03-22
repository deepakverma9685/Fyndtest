package com.deepak.movielisting.ui.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deepak.movielisting.data.local.dao.MoviesDao
import com.deepak.movielisting.data.remote.Resource
import com.deepak.movielisting.data.remote.UrlServcies
import com.deepak.movielisting.data.repository.MoviesRepo
import com.deepak.movielisting.ui.models.SearchResultsItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Deepak Kumar Verma on 10-01-2021.
 * Company Shantiinfotech.
 */
class SearchViewModel @Inject constructor(movieDao: MoviesDao,urlServcies: UrlServcies) : ViewModel() {

    private val movieRepository = MoviesRepo(urlServcies,movieDao)
    private val moviesLiveData = MutableLiveData<Resource<List<SearchResultsItem>>>()

    @SuppressLint("CheckResult")
    fun searchMovie(text: String) {
        movieRepository.searchMovies( text)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource -> moviesLiveData.postValue(resource) }
    }

    fun getMoviesLiveData() = moviesLiveData

}