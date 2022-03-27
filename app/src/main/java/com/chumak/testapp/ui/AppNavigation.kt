package com.chumak.testapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chumak.testapp.ui.screens.details.UserDetailsScreen
import com.chumak.testapp.ui.screens.list.UserListScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

const val LIST_SCREEN = "list"
const val DETAILS_SCREEN = "details"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LIST_SCREEN) {
        composable(route = LIST_SCREEN) {
            UserListScreen(
                viewModel = getViewModel(),
                navController = navController
            )
        }
        composable(
            route = "$DETAILS_SCREEN/{login}",
            arguments = listOf(navArgument("login") { type = NavType.StringType })
        ) { backStackEntry ->
            UserDetailsScreen(
                viewModel = getViewModel(parameters = {
                    parametersOf(backStackEntry.arguments?.getString("login"))
                }),
                navController = navController
            )
        }
    }
}
