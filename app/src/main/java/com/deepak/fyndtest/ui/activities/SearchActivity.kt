package com.deepak.fyndtest.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepak.fyndtest.R
import com.deepak.fyndtest.databinding.ActivitySearchBinding
import com.deepak.fyndtest.factory.ViewModelFactory
import com.deepak.fyndtest.ui.adapters.SearchResultAdapter
import com.deepak.fyndtest.ui.models.SearchResultsItem
import com.deepak.fyndtest.ui.viewmodels.SearchViewModel
import com.deepak.fyndtest.utils.EqualSpacing
import dagger.android.AndroidInjection
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {
    private val TAG = "SearchActivity"

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    private lateinit var searchViewModel: SearchViewModel
    val searchlist = ArrayList<SearchResultsItem>()
    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: SearchResultAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        initializeview()
    }

    private fun initializeview() {
        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        setupRecy()

        searchViewModel.getMoviesLiveData().observe(this, Observer { resource ->
            if(resource.isLoading){
                binding.progress.visibility = View.VISIBLE
            }else
            if (resource.isSuccess) {
                binding.progress.visibility = View.GONE
                adapter.setSearchResultsItemList(resource.data as MutableList<SearchResultsItem>)
            }
        })

        binding.ivsearch.setOnClickListener {
            adapter.setSearchResultsItemList(searchlist)
            adapter.notifyDataSetChanged()
            val query = binding.etsearch.text.toString()
            if (query.isEmpty()) {
                Toast.makeText(this, "Please enter search ", Toast.LENGTH_SHORT).show()
            } else {
                searchViewModel.searchMovie(query)
            }
        }
    }

    fun setupRecy() {
        binding.recysearch.layoutManager = LinearLayoutManager(this)
        binding.recysearch.hasFixedSize()
        binding.recysearch.addItemDecoration(EqualSpacing(16, this))
        adapter = SearchResultAdapter(this, searchlist)
        binding.recysearch.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}