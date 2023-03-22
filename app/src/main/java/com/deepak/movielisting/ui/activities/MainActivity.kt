package com.deepak.movielisting.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepak.movielisting.R
import com.deepak.movielisting.databinding.ActivityMainBinding
import com.deepak.movielisting.ui.adapters.SearchResultAdapter
import com.deepak.movielisting.ui.models.SearchResultsItem
import com.deepak.movielisting.ui.viewmodels.MainViewModel
import com.deepak.movielisting.utils.EqualSpacing
import com.deepak.movielisting.utils.RecyclerViewPaginator
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

        mainViewModel.getMoviesLiveData().observe(this, Observer { resource ->
            if (resource.isLoading) {
                binding.progress.visibility = View.VISIBLE
            } else
                if (resource.isSuccess) {
                    binding.progress.visibility = View.GONE
                    Log.e(TAG, "initializeview: " )
                    //  adapter.setSearchResultsItemList(resource.data as MutableList<SearchResultsItem>)
                    adapter.setItems(resource.data as MutableList<SearchResultsItem>)
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

        binding.recymain.addOnScrollListener(object : RecyclerViewPaginator(binding.recymain) {
            override val isLastPage: Boolean
                get() = mainViewModel.isLastPage()

            override fun loadMore(page: Long) {
                Log.e(TAG, "loadMore() called with: page = $page")
                mainViewModel.loadMoreMovies(page)
            }

            override fun loadFirstData(page: Long) {
                Log.e(TAG, "loadFirstData() called with: page = $page")
                mainViewModel.loadMoreMovies(page)
            }
        })

        adapter.setOnItemClickListener(object : SearchResultAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val movie = adapter.searchResultsItem[position]
                Log.e(TAG, "onItemClick: "+movie )
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("movie", movie)
                intent.putExtra("helllo", "1")
                startActivity(intent)
            }

        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_search -> {
                val into = Intent(this,SearchActivity::class.java)
                startActivity(into)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}