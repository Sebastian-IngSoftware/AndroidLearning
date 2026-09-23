package com.learningasjc.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learningasjc.R
import com.learningasjc.data.FruitTranslated
import com.learningasjc.data.Resources.allFruits

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel()
) {
    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)
    val gameUiState by gameViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val fruitsTranslated = remember {
        allFruits.map { fruit ->
            FruitTranslated(
                name = context.getString(fruit.fruitName),
                hint = context.getString(fruit.fruitHint),
            )
        }
    }

    LaunchedEffect(Unit) {
        gameViewModel.setFruits(fruitsTranslated)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(mediumPadding)
            .fillMaxSize()
            .safeDrawingPadding()
            .statusBarsPadding(),
    ) {
        Text(
            text = stringResource(R.string.title_app),
            style = typography.titleLarge,
        )
        GameLayout(
            modifier = Modifier
                .padding(mediumPadding)
                .fillMaxWidth(),
            currentCount = gameUiState.currentWordCount,
            currentFruitScrambled = gameUiState.currentWordScrambled,
            currentHint = gameUiState.currentHint,
            isGuessWrong = gameUiState.isGuessedWordWrong,
            userGuess = gameViewModel.userGuess,
            onValueChange = { gameViewModel.updateUserGuess(it) },
            onDoneAction = { gameViewModel.checkUserGuess() },
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(mediumPadding)
                .fillMaxWidth()
        ) {
            Button(
                onClick = { gameViewModel.checkUserGuess() },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.submit)
                )
            }
            OutlinedButton(
                onClick = { gameViewModel.skipWord() },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.skip)
                )
            }
        }
        GameStatus(
            modifier = Modifier.padding(20.dp), score = gameUiState.score
        )
    }
}

@Composable
fun GameLayout(
    modifier: Modifier = Modifier,
    currentCount: Int,
    currentFruitScrambled: String,
    currentHint: String,
    isGuessWrong: Boolean,
    userGuess: String,
    onValueChange: (String) -> Unit,
    onDoneAction: () -> Unit,
) {
    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding),
        ) {
            Text(
                text = stringResource(R.string.word_count, currentCount),
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(shape = shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )
            Text(
                text = currentFruitScrambled,
                style = typography.headlineLarge,
            )
            Text(
                text = stringResource(R.string.instructions, currentHint),
                style = typography.titleMedium,
            )
            OutlinedTextField(
                value = userGuess,
                onValueChange = onValueChange ,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                label = {
                    if (isGuessWrong) {
                        Text(
                            text = stringResource(R.string.wrong_guess)
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.enter_your_word)
                        )
                    }

                },
                isError = isGuessWrong,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onDoneAction()} ))
        }
    }
}

@Composable
fun GameStatus(modifier: Modifier, score: Int) {
    Card(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.score, score),
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}