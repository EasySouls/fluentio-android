package dev.tarjanyicsanad.fluentio.android.quizzes.di

import dev.tarjanyicsanad.fluentio.android.quizzes.ui.QuizViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val quizModule = module {
    viewModelOf(::QuizViewModel)
}