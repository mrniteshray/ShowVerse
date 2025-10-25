package xcom.niteshray.xapps.showverse.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import xcom.niteshray.xapps.showverse.presentation.detail.DetailScreen
import xcom.niteshray.xapps.showverse.presentation.home.HomeScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Detail : Screen("detail/{titleId}") {
        fun createRoute(titleId: Int) = "detail/$titleId"
    }
}

@Composable
fun ShowVerseNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onContentClick = { titleId ->
                    navController.navigate(Screen.Detail.createRoute(titleId))
                }
            )
        }
        
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("titleId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val titleId = backStackEntry.arguments?.getInt("titleId") ?: return@composable
            DetailScreen(
                titleId = titleId,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
