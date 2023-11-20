package com.example.meetchi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.util.Screen

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
                Home()
            }
        }
    }
}

@Composable
fun Home(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val items = listOf(
        Screen.Like,
        Screen.Chat,
        Screen.Profile,
    )

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            when (screen) {
                                Screen.Like -> Icon(Icons.Filled.Favorite, contentDescription = null)
                                Screen.Chat -> Icon(Icons.Filled.Send, contentDescription = null)
                                Screen.Profile -> Icon(Icons.Filled.Person, contentDescription = null)
                            }
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Like.route, Modifier.padding(innerPadding)) {
            composable(Screen.Like.route) {
                // Content for the Like screen
                SwipeScreen()
            }
            composable(Screen.Chat.route) {
                // Content for the Chat screen
                ChatScreen()
            }
            composable(Screen.Profile.route) {
                // Content for the Profile screen
                ProfileScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    MeetchiTheme {
        Home()
    }
}