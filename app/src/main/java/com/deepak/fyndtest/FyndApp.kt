package com.deepak.fyndtest

import android.app.Activity
import android.app.Application
import com.deepak.fyndtest.di.module.ApiModule
import com.deepak.fyndtest.di.component.DaggerAppComponent
import com.deepak.fyndtest.di.module.DbModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Deepak Kumar Verma on 10-01-2021.
 * Company Shantiinfotech.
 */
class FyndApp : Application(), HasActivityInjector {

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