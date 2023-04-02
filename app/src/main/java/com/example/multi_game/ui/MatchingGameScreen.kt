package com.example.multi_game.ui

import android.os.Handler
import android.os.Looper
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.multi_game.R

@Composable
fun MatchingGameScreen() {
    var gameTiles by remember { mutableStateOf(getNewGameTiles()) }
    var matchedTiles by remember { mutableStateOf(setOf<Int>()) }
    var flippedTiles by remember { mutableStateOf(setOf<Int>()) }
    var canFlip by remember { mutableStateOf(true) }
    var matchingCount by remember { mutableStateOf(0) }
    Column {
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Matching count: $matchingCount",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
        LazyVerticalGrid(
            GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(gameTiles.size) { index ->
                Tile(
                    imageResource = gameTiles[index],
                    index = index,
                    isFlipped = index in flippedTiles || index in matchedTiles,
                    canFlip = canFlip,
                    onTileClicked = { clickedIndex ->
                        if (canFlip && clickedIndex !in flippedTiles && clickedIndex !in matchedTiles) {
                            flippedTiles = flippedTiles + clickedIndex
                            if (flippedTiles.size == 2) {
                                canFlip = false
                                val (tileIndex1, tileIndex2) = flippedTiles.toList()
                                if (gameTiles[tileIndex1] == gameTiles[tileIndex2]) {
                                    matchedTiles = matchedTiles + tileIndex1 + tileIndex2
                                    matchingCount++
                                }
                                Handler(Looper.getMainLooper()).postDelayed({
                                    flippedTiles = setOf()
                                    canFlip = true
                                }, 1000)
                            }
                        }
                    }
                )
            }
        }
        Column {
            var showDialog = remember { mutableStateOf(false) }
            if (matchingCount == 6) {
                showDialog.value = true
            }

            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text(stringResource(R.string.congratulations)) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog.value = false
                                gameTiles = getNewGameTiles()
                                matchedTiles = setOf()
                                flippedTiles = setOf()
                                canFlip = true
                                matchingCount = 0
                            }
                        ) {
                            Text(text = stringResource(R.string.play_again))
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun Tile(
    imageResource: Int,
    index: Int,
    isFlipped: Boolean,
    canFlip: Boolean,
    onTileClicked: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clickable {
                onTileClicked(index)
            }
    ) {
        if (isFlipped) {
            val rotationDegrees by animateFloatAsState(if (isFlipped) 360f else 0f)
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(rotationDegrees),
                contentScale = ContentScale.FillBounds
            )
        } else {
            val backImageResource = R.drawable.back_image
            Image(
                painter = painterResource(id = backImageResource),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

private fun getNewGameTiles(): List<Int> {
    val images = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6
    )
    return (images + images).shuffled()
}