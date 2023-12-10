package com.example.meetchi.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.meetchi.model.Exclude
import com.example.meetchi.model.User
import com.example.meetchi.navigation.ScreenHome
import com.example.meetchi.ui.theme.MeetchiTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


/*
*******************************************************
*             Activity: HomeActivity                  *
*******************************************************
|  Description:                                       |
|  Activité principale pour l'écran d'accueil de      |
|  l'application. Initialise la connexion à Firestore |
|  pour récupérer les informations de l'utilisateur.  |
*******************************************************
*/
class HomeActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
                var userDataLoaded by remember { mutableStateOf(false) }
                var excludeDataLoaded by remember { mutableStateOf(false) }
                var swipeDataLoaded by remember { mutableStateOf(false) }
                val db = Firebase.firestore
                var userDB by remember { mutableStateOf(User()) }
                val listSwipe by remember { mutableStateOf(ArrayList<User>()) }
                var excludeSwitch by remember { mutableStateOf(Exclude()) }
                if(userDataLoaded){
                    if( excludeDataLoaded){
                        if(swipeDataLoaded){
                            Home(mutableStateOf(userDB), mutableStateOf(listSwipe))
                        } else {
                            db.collection("User")
                                .whereEqualTo("account_ready",true)
                                .whereEqualTo("genre",getGenderSearch(userDB))
                                .limit(100)
                                .get()
                                .addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        if(document.id != FirebaseAuth.getInstance().uid.toString() && !excludeSwitch.contains_uid(document.id)){
                                            val user = document.toObject<User>()
                                            listSwipe.add(user)
                                        }
                                    }
                                    swipeDataLoaded = true
                                }
                                .addOnFailureListener { exception ->
                                    Log.w("Firestore:Log", "Error getting documents: ", exception)
                                }
                        }
                    } else {
                        db.collection("Exclude")
                            .get()
                            .addOnSuccessListener { documents->
                                for(document in documents){
                                    val uid = document.toObject<Exclude>()
                                    Log.d("Firestore:Log","Exclude ${uid.last_insert} ${uid.uid_1} ${uid.uid_2} ")
                                    excludeSwitch = uid
                                }
                                excludeDataLoaded = true
                            }
                    }
                }else{
                    db.collection("User").document(FirebaseAuth.getInstance().uid.toString()).get().addOnSuccessListener { document ->
                        if (document != null) {
                            userDB = document.toObject<User>()!!
                        } else {
                            Log.d("Firestore:Log", "Document Failed")
                        }
                        userDataLoaded = true
                    }.addOnFailureListener { exception ->
                        Log.d("Firestore:Log", "get failed with ", exception)
                    }
                }
            }
        }
    }
}

fun getGenderSearch(user: User): String{
    return when(user.genre){
        "1"-> {"2"}
        "2"-> {"1"}
        "3"-> {"3"}
        else -> {"3"}
    }
}

/*
*******************************************************
*         Fonction Composable: Home                   *
*******************************************************
|  Description:                                       |
|  Compose l'écran d'accueil de l'application,        |
|  utilisant la bibliothèque de navigation            |
|  pour gérer la navigation entre les différentes     |
|  sections. Affiche également la barre de navigation |
|  inférieure avec les icônes des sections.           |
*******************************************************
*/
@SuppressLint("UnrememberedMutableState")
@Composable
fun Home(userDB : MutableState<User> ,listSwipe: MutableState<ArrayList<User>>) {
    val navController = rememberNavController()

    val items = listOf(
        ScreenHome.Like,
        ScreenHome.Chat,
        ScreenHome.Profile,
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
                                ScreenHome.Like -> Icon(Icons.Filled.Favorite, contentDescription = null)
                                ScreenHome.Chat -> Icon(Icons.Filled.Send, contentDescription = null)
                                ScreenHome.Profile -> Icon(Icons.Filled.Person, contentDescription = null)
                            }
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = ScreenHome.Like.route, Modifier.padding(innerPadding)) {
            composable(ScreenHome.Like.route) {
                // Contenu pour l'écran "Swipe"
                SwipeScreen(userDB = userDB, listSwipe = listSwipe)
            }
            composable(ScreenHome.Chat.route) {
                // Contenu pour l'écran "Chat"
                ChatScreen()
            }
            composable(ScreenHome.Profile.route) {
                // Contenu pour l'écran "Profile"
                ProfileScreen(userDB = userDB)
            }
        }
    }
}