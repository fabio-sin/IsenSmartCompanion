package fr.isen.sintoni.isensmartcompanion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.isen.sintoni.isensmartcompanion.ui.theme.IsenSmartCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IsenSmartCompanionTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") { MainScreen() }
                        composable("events") { EventsScreen() }
                        composable("history") { HistoryScreen() }
                    }
                }

            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var userInput by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.isen_logo),
                contentDescription = context.getString(R.string.isen_logo),
                modifier = Modifier.size(200.dp, 100.dp)
            )

            Text(text = context.getString(R.string.smart_companion), fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))

            Text(text = response, fontSize = 16.sp, modifier = Modifier.padding(top = 16.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically)
        {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text(context.getString(R.string.ask_a_question)) },
                modifier = modifier.fillMaxWidth(),
                trailingIcon = {
                    Button(
                        onClick = {
                            Toast.makeText(context, context.getString(R.string.question_submitted), Toast.LENGTH_SHORT).show()
                            response = "Response"
                        },
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = context.getString(R.string.send),
                            tint = Color.White
                        )
                    }
                }

            )
        }
    }
}

@Composable
fun EventsScreen() {
    val context = LocalContext.current

    Button(
        onClick = {
            val intent = Intent(context, EventDetailActivity::class.java)
            context.startActivity(intent)
        },
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
        colors = ButtonDefaults.buttonColors(Color.Red)
    ) {
        Text(
            text = context.getString(R.string.event_details_activity),
            color = Color.White
        )
    }
}

@Composable
fun HistoryScreen() {
}

@Composable
fun BottomNavBar(navController: NavController) {
    val context = LocalContext.current

    val currentDest = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar{
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = context.getString(R.string.home)) },
            label = { context.getString(R.string.home)},
            selected = currentDest == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = context.getString(R.string.events)) },
            label = { context.getString(R.string.events) },
            selected = currentDest == "events",
            onClick = { navController.navigate("events") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Refresh, contentDescription = context.getString(R.string.history)) },
            label = { context.getString(R.string.history) },
            selected = currentDest == "history",
            onClick = { navController.navigate("history") }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IsenSmartCompanionTheme {
        MainScreen()
    }
}