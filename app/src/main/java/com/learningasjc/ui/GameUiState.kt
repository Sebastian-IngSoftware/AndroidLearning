package com.learningasjc.ui

data class GameUiState(
    val currentWordScrambled: String = "",
    val currentHint: String = "",
    val currentWordCount: Int = 0,
    val score: Int = 0,
    val isGuessedWordWrong: Boolean = false,
    val isGameOver: Boolean = false,
)
