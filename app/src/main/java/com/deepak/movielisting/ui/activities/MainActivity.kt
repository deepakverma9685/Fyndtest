package com.deepak.movielisting.ui.activities


import androidx.navigation.Navigation
import com.deepak.movielisting.R
import com.deepak.movielisting.base.BaseActivity
import com.deepak.movielisting.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main;
    }

    override fun initViews() {

    }

    override fun observeViewModel() {

    }

}