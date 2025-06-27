package dev.tarjanyicsanad.fluentio.android.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.tarjanyicsanad.fluentio.android.FluentioDatabase
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): FluentioDatabase {
        return Room.databaseBuilder(
            app,
            FluentioDatabase::class.java,
            "fluentio_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMessageDao(db: FluentioDatabase) = db.quizDao()
}