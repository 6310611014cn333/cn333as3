package com.example.multi_game.data.quiz

data class QuizGameUiState (
        val score: Int = 0,
        val isFinished: Boolean = false,
        val currentQuestionIndex: Int = 0,
        val ansQuestionList: ArrayList<Int> = arrayListOf<Int>(0,1,2,3,4,5,6,7,8,9),
        val currentQuestionCount: Int = 1,
)