package dev.tarjanyicsanad.fluentio.android

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.tarjanyicsanad.fluentio.android.quizzes.data.QuizDao
import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.AnswerOptionEntity
import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.QuestionEntity
import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.QuizEntity

@Database(entities = [QuizEntity::class, QuestionEntity::class, AnswerOptionEntity::class], version = 1, exportSchema = true)
abstract class FluentioDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
}