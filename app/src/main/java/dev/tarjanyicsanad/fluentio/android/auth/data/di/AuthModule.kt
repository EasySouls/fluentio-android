package dev.tarjanyicsanad.fluentio.android.auth.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tarjanyicsanad.fluentio.android.auth.data.AuthService
import dev.tarjanyicsanad.fluentio.android.auth.data.FirebaseAuthService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    @Singleton
    fun bindAuthService(authService: FirebaseAuthService): AuthService
}