package fr.isen.sintoni.isensmartcompanion

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomNavBar(navController: NavController) {
    val context = LocalContext.current

    val currentDest = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = context.getString(R.string.home)
                )
            },
            label = { context.getString(R.string.home) },
            selected = currentDest == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_events),
                    contentDescription = context.getString(R.string.events)
                )
            },
            label = { context.getString(R.string.events) },
            selected = currentDest == "events",
            onClick = { navController.navigate("events") }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = context.getString(R.string.history)
                )
            },
            label = { context.getString(R.string.history) },
            selected = currentDest == "history",
            onClick = { navController.navigate("history") }
        )
    }
}
