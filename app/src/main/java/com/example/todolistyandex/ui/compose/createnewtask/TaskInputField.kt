package com.example.todolistyandex.ui.compose.createnewtask

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistyandex.R
import com.example.todolistyandex.ui.theme.BlueDark
import com.example.todolistyandex.ui.theme.CustomTheme

@Composable
fun TaskInputField(taskText: String, onTaskTextChange: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(top = 24.dp),
        colors = CardDefaults.cardColors(
            containerColor = CustomTheme.colors.backSecondary,
            contentColor = CustomTheme.colors.labelPrimary
        )
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            TextField(
                placeholder = {
                    Text(text = stringResource(R.string.what_to_do), color = CustomTheme.colors.labelTertiary, fontSize = 16.sp)
                },
                value = taskText,
                onValueChange = onTaskTextChange,
                modifier = Modifier.fillMaxSize(),
                textStyle = LocalTextStyle.current.copy(color = CustomTheme.colors.labelPrimary, fontSize = 16.sp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = CustomTheme.colors.labelPrimary,
                    placeholderColor = CustomTheme.colors.labelTertiary,
                    backgroundColor = CustomTheme.colors.backSecondary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = BlueDark
                ),
            )
        }
    }
}