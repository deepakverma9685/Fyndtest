package com.deepak.fyndtest.di.component

import android.app.Application
import com.deepak.fyndtest.FyndApp
import com.deepak.fyndtest.di.module.ActivityModule
import com.deepak.fyndtest.di.module.ApiModule
import com.deepak.fyndtest.di.module.DbModule
import com.deepak.fyndtest.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Deepak Kumar Verma on 10-01-2021.
 * Company Shantiinfotech.
 */


@Singleton
@Component(modules = [ApiModule::class,AndroidSupportInjectionModule::class,ActivityModule::class,DbModule::class
,ViewModelModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun apiModule(apiModule: ApiModule): Builder

        @BindsInstance
        fun dbModule(dbModule: DbModule): Builder

        fun build(): AppComponent
    }

    fun  inject(fyndApp: FyndApp)

}