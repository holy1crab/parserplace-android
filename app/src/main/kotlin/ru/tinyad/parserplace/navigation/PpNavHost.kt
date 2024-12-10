package ru.tinyad.parserplace.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import kotlinx.serialization.Serializable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.tinyad.parserplace.R
import ru.tinyad.parserplace.feature.account.AccountScreen
import ru.tinyad.parserplace.feature.subscription.SubscriptionsScreen

@Serializable
data object SubscriptionsRoute

@Serializable
data class SubscriptionDetailsRoute(val subscriptionId: Int)

@Serializable
data object AccountRoute


@Composable
fun PpNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = SubscriptionsRoute,
        modifier = modifier
    ) {
        composable<SubscriptionsRoute> { SubscriptionsScreen() }
        composable<AccountRoute> { AccountScreen() }
    }
}

data class BottomNavigationRoute<T>(
    val labelId: Int,
    val icon: ImageVector,
    val route: T,
)

val bottomNavigationItems = listOf(
    BottomNavigationRoute(
        labelId = R.string.bottom_nav_subscriptions,
        icon = Icons.Filled.Home,
        route = SubscriptionsRoute
    ),
    BottomNavigationRoute(
        labelId = R.string.bottom_nav_account,
        icon = Icons.Filled.AccountCircle,
        route = AccountRoute
    ),
)
