package com.example.multi_game.data.quiz

data class Question(
    val questions: String,
    val choices: List<String>,
    val correctAnswer: String
)