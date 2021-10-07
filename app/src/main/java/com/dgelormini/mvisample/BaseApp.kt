package com.dgelormini.mvisample

import android.app.Application
import com.airbnb.mvrx.Mavericks
import timber.log.Timber

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Mavericks.initialize(this)
    }
}