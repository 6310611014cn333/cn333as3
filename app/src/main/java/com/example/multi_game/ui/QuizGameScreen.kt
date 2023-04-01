package com.example.multi_game.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.multi_game.R

@Composable
fun QuizGameScreen(
    modifier: Modifier = Modifier,
    gameviewModel: QuizGameViewModel = viewModel()
) {
    val gameUiState by gameviewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (!gameUiState.isFinished){
            GameStatus()
            GameLayout()
        }
        else {
            FinalScoreDialog(score = gameUiState.score,
                onPlayAgain = {
                    gameviewModel.resetGame()
                }
            )
        }
    }
}

@Composable
fun GameStatus(
    modifier: Modifier = Modifier,
    gameviewModel: QuizGameViewModel = viewModel(),
) {
    val gameUiState by gameviewModel.uiState.collectAsState()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp),
    ) {
        Text(
            text = stringResource(R.string.question_count, gameUiState.currentQuestionCount),
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = stringResource(R.string.score, gameUiState.score),
            fontSize = 18.sp
        )
    }
}

@Composable
fun GameLayout(
    modifier: Modifier = Modifier,
    gameviewModel: QuizGameViewModel = viewModel()
) {

    var currentQuestion by remember { mutableStateOf(gameviewModel.nextQuestion()) }
    var choiceClicked by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = currentQuestion!!.questions,
            fontSize = 40.sp,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(25.dp)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    currentQuestion!!.choices.shuffled().forEach { choice ->
        Row(modifier = Modifier.fillMaxWidth().height(64.dp)) {
            TextButton(
                onClick = {
                    gameviewModel.checkAnswer(choice)
                    currentQuestion = gameviewModel.nextQuestion()
                    choiceClicked = ""
                    gameviewModel.countQuestion() },
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = choice,
                    fontSize = 20.sp,
                )
            }
        }
    }
}

@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.congratulations)) },
        text = { Text(stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onPlayAgain()
                }
            ) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}