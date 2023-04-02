package com.example.multi_game
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.multi_game.ui.MatchingGameScreen
import com.example.multi_game.ui.PickGameScreen
import com.example.multi_game.ui.QuizGameScreen
import com.example.multi_game.ui.theme.NumberGuessingGameScreen

enum class MultiGameScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Game(title = R.string.game),
    NumberGuessing(title = R.string.number_guessing),
    Quiz(title = R.string.quiz),
    MatchingGame(title = R.string.matching_game),
}

@Composable
fun MultiGameAppBar(
    currentScreen: MultiGameScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun MultiGameScreen(
    modifier: Modifier = Modifier,
//    viewModel: GameViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MultiGameScreen.valueOf(backStackEntry?.arguments?.getString("name") ?: MultiGameScreen.Start.name)

    Scaffold(
        topBar = {
            MultiGameAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
//        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = MultiGameScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = MultiGameScreen.Start.name) {
                PickGameScreen(navController = navController)
            }
            composable(route = MultiGameScreen.Game.name + "/{name}",
                arguments = listOf(navArgument("name"){
                    type = NavType.StringType
                })) {
                val name = requireNotNull(it.arguments).getString("name")
                if (name != null) {
                    if (name == "NumberGuessing")
                        NumberGuessingGameScreen()
                    if (name == "Quiz")
                        QuizGameScreen()
                    if (name == "MatchingGame")
                        MatchingGameScreen()
                }
            }
        }
    }
}