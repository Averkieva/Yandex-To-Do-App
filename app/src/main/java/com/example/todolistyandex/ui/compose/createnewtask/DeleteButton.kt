package com.example.todolistyandex.ui.compose.createnewtask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.todolistyandex.R
import com.example.todolistyandex.ui.theme.CustomTheme
import com.example.todolistyandex.ui.viewmodel.TaskViewModel

@Composable
fun DeleteButton(taskText: String, taskViewModel: TaskViewModel, navController: NavController) {
    val deleteColor = if (taskText.isEmpty()) CustomTheme.colors.labelDisable else Color.Red

    ConstraintLayout(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        val (deleteButton, deleteText) = createRefs()

        IconButton(
            onClick = {
                taskViewModel.deleteTodoItem()
                navController.popBackStack()
            },
            modifier = Modifier.constrainAs(deleteButton) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = deleteColor)
        }

        Text(
            text = stringResource(R.string.delete),
            color = deleteColor,
            fontSize = 16.sp,
            modifier = Modifier.constrainAs(deleteText) {
                start.linkTo(deleteButton.end, margin = 8.dp)
                top.linkTo(deleteButton.top)
                bottom.linkTo(deleteButton.bottom)
            }
        )
    }
}