package io.github.takusan23.material3tonalelevationcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.takusan23.material3tonalelevationcalc.ui.theme.Material3TonalElevationCalcTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val inputColor = remember { mutableStateOf("#000000") }
    val tonalElevation = remember { mutableStateOf("1") }
    val outputColorString = remember { mutableStateOf(inputColor.value) }
    val outputColor = remember { mutableStateOf(Color.White) }
    val colorScheme = MaterialTheme.colorScheme

    LaunchedEffect(key1 = inputColor.value, key2 = tonalElevation.value) {
        val parseColor = runCatching {
            Color(android.graphics.Color.parseColor(inputColor.value))
        }.getOrNull()
        outputColor.value = colorScheme.copy(surface = parseColor ?: colorScheme.surface)
            .surfaceColorAtElevation((tonalElevation.value.toIntOrNull() ?: 1).dp)
        outputColorString.value = outputColor.value
            .toArgb()
            .let { "#%X".format(it) }
    }

    Material3TonalElevationCalcTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text(text = "TonalElevationを計算する") }) }
        ) {
            Column(modifier = Modifier.padding(it)) {


                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(color = outputColor.value)
                        .align(alignment = Alignment.CenterHorizontally)
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                    text = outputColorString.value,
                    fontSize = 30.sp,
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = inputColor.value,
                    onValueChange = { inputColor.value = it },
                    label = { Text(text = "Color Code") }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = tonalElevation.value,
                    onValueChange = { tonalElevation.value = it },
                    label = { Text(text = "Tonal Elevation") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

            }
        }
    }
}