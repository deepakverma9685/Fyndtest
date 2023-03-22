package com.deepak.movielisting.di.module

import com.deepak.movielisting.ui.activities.MainActivity
import com.deepak.movielisting.ui.activities.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchActivity(): SearchActivity

//    @ContributesAndroidInjector
//    abstract fun contributeMovieDetailActivity(): MovieDetailActivity
//
//    @ContributesAndroidInjector
//    abstract fun contributeTvDetailActivity(): TvDetailActivity
//
//    @ContributesAndroidInjector
//    abstract fun contributeMovieSearchActivity(): MovieSearchActivity
//
//    @ContributesAndroidInjector
//    abstract fun contributeTvSearchActivity(): TvSearchActivity
}