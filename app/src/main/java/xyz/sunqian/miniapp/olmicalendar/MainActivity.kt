package xyz.sunqian.miniapp.olmicalendar

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import xyz.sunqian.miniapp.olmicalendar.theme.MyApplicationTheme
import java.time.Duration


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(listOf("Olmi", "Yomi", "Humi"))
                }
            }
        }

        //timer
//        val constraints  = Constraints.Builder()
//            //.setRequiredNetworkType(NetworkType.CONNECTED)
//            .setRequiresCharging(true)
//            .build()
//        val workRequest = PeriodicWorkRequest.Builder(TimeWork::class.java, Duration.ofHours(1L))
//            .setConstraints(constraints)
//            .build()
//        WorkManager.getInstance(this).enqueue(workRequest)
    }
}

@Composable
fun Greeting(messages: List<String>, modifier: Modifier = Modifier) {
    var count = 1;
    LazyColumn {
        items(messages) { message ->
            var isExpanded by remember { mutableStateOf(false) }
            val extraPadding by animateDpAsState(
                if (isExpanded) 5.dp else 0.50.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            Surface(
                modifier = Modifier
                    .padding(extraPadding.coerceAtLeast(0.5.dp))
                    .border(
                        extraPadding.coerceAtLeast(0.5.dp),
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.shapes.medium
                    )
                    .background(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.shapes.extraLarge
                    )
            ) {
                Text(
                    text = "${count++}: $message",
                    modifier = modifier.clickable {
                        isExpanded = !isExpanded
                    },
                    style = if (isExpanded) MaterialTheme.typography.bodyLarge
                    else MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun GreetingPreview() {
    MyApplicationTheme(dynamicColor = false) {
        Greeting(listOf("Olmi", "Yomi", "Humi"))
    }
}