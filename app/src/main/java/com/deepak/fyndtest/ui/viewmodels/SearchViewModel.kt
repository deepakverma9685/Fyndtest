package com.deepak.fyndtest.ui.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deepak.fyndtest.data.local.dao.MoviesDao
import com.deepak.fyndtest.data.remote.Resource
import com.deepak.fyndtest.data.remote.UrlServcies
import com.deepak.fyndtest.data.repository.MoviesRepo
import com.deepak.fyndtest.ui.models.SearchResultsItem
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