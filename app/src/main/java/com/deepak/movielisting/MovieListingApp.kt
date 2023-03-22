package com.deepak.movielisting

import android.app.Activity
import android.app.Application
import com.deepak.movielisting.di.module.ApiModule
import com.deepak.movielisting.di.component.DaggerAppComponent
import com.deepak.movielisting.di.module.DbModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class MovieListingApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }


    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
                .builder()
                .application(this)
                .apiModule(ApiModule())
                .dbModule(DbModule())
                .build()
                .inject(this)
    }

}