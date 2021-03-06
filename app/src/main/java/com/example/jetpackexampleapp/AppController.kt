package com.example.jetpackexampleapp

import android.app.Activity
import android.app.Application
import com.example.jetpackexampleapp.di.Component.DaggerApiComponent
import com.example.jetpackexampleapp.di.DaggerAppComponent
import com.example.jetpackexampleapp.di.DbModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


/*
 * we use our AppComponent (now prefixed with Dagger)
 * to inject our Application class.
 * This way a DispatchingAndroidInjector is injected which is
 * then returned when an injector for an activity is requested.
 * */
class AppController : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerApiComponent.builder()
            .application(this)
            .dbModule(DbModule())
            .build()
            .inject(this)
    }
}