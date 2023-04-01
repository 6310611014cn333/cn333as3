package com.example.multi_game.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.multi_game.R

@Composable
fun PickGameScreen(
    navController: NavController, modifier: Modifier = Modifier
    ){
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(R.drawable.game_logo),
            contentDescription = null,
            modifier = Modifier.width(300.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = stringResource(R.string.pick_game), style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(6.dp))
        Button(onClick = {navController.navigate("game/NumberGuessing")},
            Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.number_guessing))
        }
        Button(onClick = {navController.navigate("game/Quiz")},
            Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.quiz))
        }
        Button(onClick = {navController.navigate("game/PairPicture")},
            Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.pair_picture))
        }
    }
}