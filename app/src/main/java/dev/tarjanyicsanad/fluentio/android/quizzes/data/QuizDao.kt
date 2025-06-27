package dev.tarjanyicsanad.fluentio.android.quizzes.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.tarjanyicsanad.fluentio.android.quizzes.data.entities.QuizEntity
import dev.tarjanyicsanad.fluentio.android.quizzes.data.relations.QuestionWithAnswers
import dev.tarjanyicsanad.fluentio.android.quizzes.data.relations.QuizWithQuestions
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Query("SELECT * FROM quizzes")
    fun getQuizzes(): Flow<List<QuizEntity>>

    @Transaction
    @Query("SELECT * FROM quizzes WHERE quizId = :quizId")
    fun getQuizWithQuestions(quizId: Int): Flow<QuizWithQuestions?>

    @Transaction
    @Query("SELECT * FROM questions WHERE quizId = :quizId")
    fun getQuestionsWithAnswers(quizId: Int): Flow<List<QuestionWithAnswers>>

    @Query("SELECT * FROM quizzes ORDER BY timestamp DESC")
    fun getMessagesPaged(): PagingSource<Int, QuizEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<QuizEntity>)

    @Query("DELETE FROM quizzes")
    suspend fun clearMessages()
}