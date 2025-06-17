package dev.tarjanyicsanad.fluentio.android

import android.app.Application
import dev.tarjanyicsanad.fluentio.android.quizzes.di.quizModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FluentioApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FluentioApplication)
            modules(quizModule)
        }
    }
}