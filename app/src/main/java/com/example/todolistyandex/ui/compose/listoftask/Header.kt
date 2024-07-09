package com.example.todolistyandex.ui.compose.listoftask

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.todolistyandex.R
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.ListOfTaskViewModel

@Composable
fun Header(
    textSize: TextUnit,
    showAdditionalText: Boolean,
    completedTaskCount: Int,
    showCompletedTasks: Boolean,
    taskViewModel: ListOfTaskViewModel
) {
    Text(
        text = stringResource(id = R.string.my_business),
        color = CustomTheme.colors.labelPrimary,
        style = MaterialTheme.typography.bodyLarge,
        fontSize = textSize,
        modifier = Modifier.padding(start = 60.dp, top = 82.dp)
    )
    if (showAdditionalText) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.done, completedTaskCount),
                color = CustomTheme.colors.labelSecondary,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { taskViewModel.toggleShowCompletedTasks() }) {
                Icon(
                    painter = painterResource(
                        id = if (showCompletedTasks) R.drawable.cell_eye else R.drawable.cell_eye_off
                    ),
                    contentDescription = stringResource(id = R.string.toggle_completed_tasks),
                    tint = CustomTheme.colors.colorBlue
                )
            }
        }
    }
}