package dev.tarjanyicsanad.fluentio.android.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val familyQuestDispatcher: AppDispatcher)

enum class AppDispatcher {
    Default,
    IO,
}