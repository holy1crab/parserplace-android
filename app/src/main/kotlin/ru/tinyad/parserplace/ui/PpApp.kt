package ru.tinyad.parserplace.ui

import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.tinyad.parserplace.feature.subscription.SubscriptionEditScreen
import ru.tinyad.parserplace.navigation.PpNavHost
import ru.tinyad.parserplace.navigation.bottomNavigationItems
import ru.tinyad.parserplace.ui.theme.PpTheme

@Composable
fun MainScreen() {

    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = "Hello world", fontSize = 16.sp)
        SubscriptionEditScreen()
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun PpApp() {

    PpTheme {
        PpScaffold()
    }
}

@Composable
fun PpScaffold() {

    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavigationItems.forEach { route ->

                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.hasRoute(route.route::class) } == true,
                        icon = {
                            Icon(route.icon, contentDescription = "")
                        },
                        onClick = {
                            navController.navigate(route.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    insets = WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal
                    )
                )
        ) {
            Box {
                PpNavHost(navController = navController)
            }
        }
    }
}
