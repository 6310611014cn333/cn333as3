package com.example.multi_game.ui.theme


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.multi_game.R

private fun theHint(guess: Int, ans: Int, rounds: Int): Pair<String, Int> {
    var hint = ""
    var round = rounds
    if (guess > ans && guess <= 1000) {
        hint = "Hint: It's lower!"
        round++
    } else if (guess < ans && guess >= 1) {
        hint = "Hint: It's higher!"
        round++
    } else if (guess == ans) {
        hint = "Correct! You won in $round round(s)!"
        round = 0
    } else {
        hint = "Guess the number!"
        round = 1
    }
    return Pair(hint, round)
}

@Composable
fun GuessingNumField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent)
    )
}

@Composable
fun NumberGuessingGameScreen() {
    var ans by remember { mutableStateOf((1..1000).random()) }
    var numInput by remember { mutableStateOf("") }
    var guess = numInput.toIntOrNull() ?: 0

    var hint by remember { mutableStateOf(theHint(guess, ans, 0).first) }
    var round by remember { mutableStateOf(theHint(guess, ans, 0).second) }

    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.game_description),
            fontSize = 24.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 2.dp)
        )

        Spacer(Modifier.height(150.dp))
        GuessingNumField(label = R.string.your_guess,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    hint = theHint(guess, ans, round).first
                    round = theHint(guess, ans, round).second
                }
            ),
            value = numInput,
            onValueChange = { numInput = it })

        Spacer(Modifier.height(150.dp))
        Text(
            text = hint,
            style = TextStyle(fontSize = 22.sp, color = Color.Gray),
            modifier = Modifier
                .padding(vertical = 8.dp),
        )

        Button(onClick = {
            ans = (1..1000).random()
            numInput = ""
            guess = numInput.toIntOrNull() ?: 0
            hint = theHint(guess, ans, 0).first
            round = theHint(guess, ans, 0).second
        }) {
            Text(stringResource(R.string.play_again))
        }

    }
}
