package com.deepak.movielisting.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deepak.movielisting.data.local.dao.MoviesDao
import com.deepak.movielisting.data.remote.Resource
import com.deepak.movielisting.data.remote.UrlServcies
import com.deepak.movielisting.data.repository.MoviesRepo
import com.deepak.movielisting.ui.models.SearchResultsItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by Deepak Kumar Verma on 10-01-2021.
 * Company Shantiinfotech.
 */
class MainViewModel @Inject constructor(urlServcies: UrlServcies,moviesDao: MoviesDao) : ViewModel() {

    private val movieRepository = MoviesRepo(urlServcies,moviesDao)
    private val moviesLiveData = MutableLiveData<Resource<List<SearchResultsItem>>>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable();

    fun loadMoreMovies(currentPage: Long) {
        movieRepository.loadmovies(currentPage)
            .doOnSubscribe { disposable -> addToDisposable(disposable) }
            .subscribe { resource -> getMoviesLiveData().postValue(resource) }
    }

    fun getMoviesLiveData() = moviesLiveData

    fun addToDisposable(disposable: Disposable) {
        compositeDisposable.remove(disposable)
        compositeDisposable.add(disposable)
    }

    fun onStop() {
        compositeDisposable.clear()
    }

    fun isLastPage(): Boolean {
        if(moviesLiveData.value != null &&
            moviesLiveData.value!!.data != null &&
            !moviesLiveData.value!!.data!!.isEmpty()) {
            return moviesLiveData.value!!.data!![0].isLastPage()
        }

        return true
    }

}