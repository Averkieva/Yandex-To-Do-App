package com.example.todolistyandex.ui.compose.createnewtask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                placeholder = {
                    Text(
                        text = stringResource(R.string.what_to_do),
                        color = CustomTheme.colors.labelTertiary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                value = taskText,
                onValueChange = onTaskTextChange,
                modifier = Modifier
                    .fillMaxSize()
                    .clearAndSetSemantics {
                        contentDescription = if (taskText.isEmpty()) {
                            "Поле ввода. Напишите что нужно сделать"
                        } else {
                            "Поле ввода. Текущая задача: $taskText"
                        }
                    }
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = true,
                            color = CustomTheme.colors.labelPrimary
                        )
                    ) {},
                textStyle = LocalTextStyle.current.copy(
                    color = CustomTheme.colors.labelPrimary,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                ),
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
