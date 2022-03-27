package com.chumak.testapp.ui

import android.app.Application
import org.koin.core.context.startKoin

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(com.chumak.testapp.ui.di.modules)
        }
    }
}