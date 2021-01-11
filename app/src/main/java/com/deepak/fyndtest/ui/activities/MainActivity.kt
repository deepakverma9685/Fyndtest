package com.deepak.fyndtest.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepak.fyndtest.R
import com.deepak.fyndtest.databinding.ActivityMainBinding
import com.deepak.fyndtest.ui.adapters.SearchResultAdapter
import com.deepak.fyndtest.ui.models.SearchResultsItem
import com.deepak.fyndtest.ui.viewmodels.MainViewModel
import com.deepak.fyndtest.ui.viewmodels.SearchViewModel
import com.deepak.fyndtest.utils.EqualSpacing
import com.deepak.fyndtest.utils.RecyclerViewPaginator
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mainViewModel: MainViewModel
    val searchlist = ArrayList<SearchResultsItem>()
    private lateinit var adapter: SearchResultAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initializeview()

    }

    private fun initializeview() {
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        setupRecy()

        mainViewModel.loadMoreMovies(1)

        mainViewModel.getMoviesLiveData().observe(this, Observer { resource ->
            if(resource.isLoading){
                binding.progress.visibility = View.VISIBLE
            }else
                if (resource.isSuccess) {
                    binding.progress.visibility = View.GONE
                    adapter.setSearchResultsItemList(resource.data as MutableList<SearchResultsItem>)
                }
        })

    }

    fun setupRecy() {
        binding.recymain.layoutManager = LinearLayoutManager(this)
        binding.recymain.hasFixedSize()
        binding.recymain.addItemDecoration(EqualSpacing(16, this))
        adapter = SearchResultAdapter(this, searchlist)
        binding.recymain.adapter = adapter
        adapter.notifyDataSetChanged()

//        binding.recymain.addOnScrollListener(object : RecyclerViewPaginator(binding.recymain) {
//            override val isLastPage: Boolean
//                get() = moviesListViewModel.isLastPage()
//
//            override fun loadMore(page: Long) {
//                moviesListViewModel.loadMoreMovies(page)
//            }
//
//            override fun loadFirstData(page: Long) {
//                displayLoader()
//                moviesListViewModel.loadMoreMovies(page)
//            }
//        })
    }
}