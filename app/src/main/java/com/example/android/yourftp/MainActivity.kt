package com.example.android.yourftp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@Composable
fun MainContent() {

    val (text, setText) = remember { mutableStateOf("100") }
    Column(
        modifier = Modifier
            .padding(top = 24.dp)
            .padding(horizontal = 24.dp)
    ) {
        InputFtp(
            text = text,
            onTextChanged = setText
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (text != "") {
            FtpZoneList(
                inputFtp = text.toInt()
            )
        }
    }
}

@Composable
fun InputFtp(
    text: String,
    onTextChanged: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Your FTP is ...", fontSize = 24.sp)
        OutlinedTextField(value = text, onValueChange = onTextChanged, maxLines = 1, placeholder = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(text = "W", modifier = Modifier.align(Alignment.CenterEnd))
            }
        })
    }
}

@Composable
fun FtpZoneList(
    inputFtp: Int
) {
    LazyColumn {
        items(powerZoneList) { powerZone ->
            PowerZoneRow(inputFtp = inputFtp, powerZone = powerZone)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
            )
        }
    }
}

@Composable
fun PowerZoneRow(
    inputFtp: Int,
    powerZone: PowerZone
) {
    Row {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = powerZone.label,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Row(modifier = Modifier.align(Alignment.TopEnd)) {
                Text(text = (powerZone.minRange * inputFtp).toInt().toString(), fontSize = 16.sp)
                Text(text = "~", fontSize = 16.sp)
                Text(
                    text = "${(powerZone.maxRange * inputFtp).toInt()}[W]",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewInputFtp() {
    InputFtp("", {})
}

val powerZoneList = listOf(
    PowerZone(
        label = "L1 回復走",
        minRange = 0f,
        maxRange = 0.55f
    ),
    PowerZone(
        label = "L2 耐久走",
        minRange = 0.56f,
        maxRange = 0.75f
    ),
    PowerZone(
        label = "L3 テンポ走",
        minRange = 0.76f,
        maxRange = 0.90f
    ),
    PowerZone(
        label = "L4 LT（乳酸閾値）",
        minRange = 0.91f,
        maxRange = 1.05f
    ),
    PowerZone(
        label = "L5 VO2max",
        minRange = 1.06f,
        maxRange = 1.2f
    ),
)

data class PowerZone(
    val label: String,
    val minRange: Float,
    val maxRange: Float,
)