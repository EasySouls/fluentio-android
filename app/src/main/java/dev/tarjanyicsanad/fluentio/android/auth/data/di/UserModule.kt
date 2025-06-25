package dev.tarjanyicsanad.fluentio.android.auth.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tarjanyicsanad.fluentio.android.auth.data.FirestoreUserRepository
import dev.tarjanyicsanad.fluentio.android.auth.data.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UserModule {

    @Binds
    @Singleton
    fun bindUserRepository(userRepository: FirestoreUserRepository): UserRepository
}