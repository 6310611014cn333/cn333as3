package com.example.multi_game.ui

import androidx.lifecycle.ViewModel
import com.example.multi_game.data.quiz.Question
import com.example.multi_game.data.quiz.questions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.multi_game.data.quiz.QuizGameUiState

class QuizGameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuizGameUiState())
    val uiState: StateFlow<QuizGameUiState> = _uiState.asStateFlow()

    fun checkAnswer(answer: String) {
        var currentQuestionIndex = _uiState.value.currentQuestionIndex
        if (answer == questions[currentQuestionIndex].correctAnswer) {
            val score = _uiState.value.score + 1
            _uiState.update { currentState ->
                currentState.copy(score = score)
            }
        }
    }

    fun countQuestion() {
        _uiState.update { currentState ->
            currentState.copy(currentQuestionCount = _uiState.value.currentQuestionCount + 1) }
    }

    fun nextQuestion(): Question? {
        var ansQuestionList =  _uiState.value.ansQuestionList
        var currentQuestion = questions[_uiState.value.currentQuestionIndex]
        if (ansQuestionList.size > 0) {
            var currentQuestionIndex = ansQuestionList.randomOrNull()?:_uiState.value.currentQuestionIndex
            currentQuestion = questions[currentQuestionIndex]
            ansQuestionList.remove(currentQuestionIndex)
            _uiState.update { currentState ->
                currentState.copy(
                    currentQuestionIndex = currentQuestionIndex,
                    ansQuestionList = ansQuestionList,
                ) }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isFinished = true,
                ) }
        }
        return currentQuestion
    }

    fun resetGame() {
        _uiState.update { currentState ->
            currentState.copy(
                score = 0,
                ansQuestionList = arrayListOf<Int>(0,1,2,3,4,5,6,7,8,9),
                isFinished = false,
                currentQuestionCount = 1,
            ) }
    }
}