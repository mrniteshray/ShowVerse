package xcom.niteshray.xapps.showverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import xcom.niteshray.xapps.showverse.presentation.navigation.ShowVerseNavigation
import xcom.niteshray.xapps.showverse.ui.theme.ShowVerseTheme

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppRoot()
        }
    }
}

@Composable
private fun AppRoot() {
    val navController = rememberNavController()

    ShowVerseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ShowVerseNavigation(navController = navController)
        }
    }
}
