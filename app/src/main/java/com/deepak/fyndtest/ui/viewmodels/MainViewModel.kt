package com.deepak.fyndtest.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deepak.fyndtest.data.local.dao.MoviesDao
import com.deepak.fyndtest.data.remote.Resource
import com.deepak.fyndtest.data.remote.UrlServcies
import com.deepak.fyndtest.data.repository.MoviesRepo
import com.deepak.fyndtest.ui.models.SearchResultsItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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