package dev.tarjanyicsanad.fluentio.android.ui.common

sealed interface NavigationEvent {
    object NavigateToHome : NavigationEvent
}