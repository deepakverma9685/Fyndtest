package com.deepak.movielisting.ui.fragments

import androidx.fragment.app.viewModels
import com.deepak.movielisting.R
import com.deepak.movielisting.base.BaseFragment
import com.deepak.movielisting.databinding.FragmentMovieListBinding
import com.deepak.movielisting.ui.viewmodels.MovieListViewModel

class MovieListFragment : BaseFragment<FragmentMovieListBinding>() {

    private val viewModel:MovieListViewModel by viewModels()

    override fun getLayoutRes(): Int {
       return R.layout.fragment_movie_list
    }

    override fun initViews() {
    }

    override fun observeViewModel() {

    }
}