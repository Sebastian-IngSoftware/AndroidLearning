package com.learningasjc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.learningasjc.R

@Composable
fun GameScreen() {
    val mediumPadding = dimensionResource(id = R.dimen.padding_medium)
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
                .fillMaxWidth()
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(mediumPadding)
                .fillMaxWidth()
        ) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.submit)
                )
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.skip)
                )
            }
        }
        GameStatus(modifier = Modifier.padding(20.dp))
    }
}

@Composable
fun GameLayout(modifier: Modifier = Modifier) {
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
                text = stringResource(R.string.word_count, 0),
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(shape = shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )
            Text(
                text = "Apple",
                style = typography.headlineLarge,
            )
            Text(
                text = stringResource(R.string.instructions, "this is a hint"),
                style = typography.titleMedium,
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                label = {
                    Text(
                        text = stringResource(R.string.enter_your_word)
                    )
                })
        }
    }
}

@Composable
fun GameStatus(modifier: Modifier) {
    Card(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.score, 0),
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}