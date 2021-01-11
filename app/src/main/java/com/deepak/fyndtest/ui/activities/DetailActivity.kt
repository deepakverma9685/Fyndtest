package com.deepak.fyndtest.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.deepak.fyndtest.R
import com.deepak.fyndtest.databinding.ActivityDetailBinding
import com.deepak.fyndtest.ui.models.SearchResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.searchresultitem.view.*

class DetailActivity : AppCompatActivity() {
    private val TAG = "DetailActivity"
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        initializevew()
    }

    private fun initializevew() {
        val movie = intent.getSerializableExtra("movie") as SearchResultsItem
        val helllo = intent.getSerializableExtra("helllo")
        Log.e(TAG, "initializevew: $movie   $helllo" )
        binding.tvname.text = movie.title
        binding.tvoverview.text = movie.overview
        val imageUrl = movie.getFormattedPosterPath()
        Picasso.get().load(imageUrl).into(binding.ivimage)

    }
}