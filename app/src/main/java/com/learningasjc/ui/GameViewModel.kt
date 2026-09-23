package com.learningasjc.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.learningasjc.data.Fruit
import com.learningasjc.data.FruitTranslated
import com.learningasjc.data.Resources.MAX_NO_OF_WORDS
import com.learningasjc.data.Resources.SCORE_INCREASE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState()) // Para el Viewmodel
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow() // Para la UI

    private lateinit var currentFruit: FruitTranslated
    private var usedFruit: MutableSet<FruitTranslated> = mutableSetOf()
    private var availableFruits: List<FruitTranslated> = emptyList()

    var userGuess by mutableStateOf("")
        private set

    fun setFruits(fruits: List<FruitTranslated>) {
        availableFruits = fruits
        resetGame()
    }

    fun resetGame() {
        usedFruit.clear()
        val fruit = pickRandomFruit()
        _uiState.value = GameUiState(currentWordScrambled = fruit.name, currentHint = fruit.hint)
    }

    private fun pickRandomFruit(): FruitTranslated {
        currentFruit = availableFruits.random()
        return if (usedFruit.contains(currentFruit)){
            pickRandomFruit()
        }else{
            usedFruit.add(currentFruit)
            shuffleCurrentFruit(currentFruit)
        }
    }

    private fun shuffleCurrentFruit(fruit: FruitTranslated): FruitTranslated
    {
        val tempFruit = fruit.name.toCharArray()
        tempFruit.shuffle()
        while (String(tempFruit) == fruit.name) {
            tempFruit.shuffle()
        }
        return fruit.copy(name = String(tempFruit))
    }

    fun updateUserGuess(newValue: String) {
        userGuess = newValue
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentFruit.name, ignoreCase = true)){
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        updateUserGuess("")
    }

    fun skipWord(){
        updateGameState(_uiState.value.score)
        updateUserGuess("")
    }

    fun updateGameState(updatedScore: Int){
        if (usedFruit.size == MAX_NO_OF_WORDS){
            _uiState.update {currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else {
            val nextFruit = pickRandomFruit()
            _uiState.update {currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentWordScrambled = nextFruit.name,
                    currentHint = nextFruit.hint,
                    score = updatedScore,
                    currentWordCount = currentState.currentWordCount.inc()
                )
            }
        }

    }
}