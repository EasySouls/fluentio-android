package dev.tarjanyicsanad.fluentio.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.tarjanyicsanad.fluentio.android.quizzes.di.quizModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@HiltAndroidApp
class FluentioApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FluentioApplication)
            modules(quizModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}