package com.example.todolistyandex.ui.compose.listoftask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.todolistyandex.ui.theme.CustomTheme

@Composable
fun ErrorScreen(fetchError: String?, retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = fetchError ?: "Неизвестная ошибка",
                color = Color.Red,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = retryAction,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomTheme.colors.colorBlue,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Повторить",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}
