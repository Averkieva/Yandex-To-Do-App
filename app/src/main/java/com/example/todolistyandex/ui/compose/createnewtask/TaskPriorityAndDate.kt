package com.example.todolistyandex.ui.compose.createnewtask

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.todolistyandex.R
import com.example.todolistyandex.data.model.ListOfTaskStatus
import com.example.todolistyandex.ui.theme.BlueDark
import com.example.todolistyandex.ui.theme.BlueLight
import com.example.todolistyandex.ui.theme.CustomTheme

@Composable
fun TaskPriorityAndDate(
    priority: ListOfTaskStatus,
    isPriorityDropdownExpanded: Boolean,
    deadlineComplete: String,
    isDeadlineSet: Boolean,
    priorityItems: List<ListOfTaskStatus>,
    onPriorityDropdownExpandedChange: (Boolean) -> Unit,
    onPriorityChange: (ListOfTaskStatus) -> Unit,
    onDeadlineSwitchChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    ConstraintLayout(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        val (priorityLabel, priorityText, dateLabel, dateSwitch, dateSetText, divider, divider2) = createRefs()

        Text(
            text = stringResource(R.string.importance),
            modifier = Modifier.constrainAs(priorityLabel) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            },
            color = CustomTheme.colors.labelPrimary,
            fontSize = 16.sp
        )

        Box(
            modifier = Modifier
                .constrainAs(priorityText) {
                    start.linkTo(parent.start)
                    top.linkTo(priorityLabel.bottom, margin = 4.dp)
                }
                .background(CustomTheme.colors.backPrimary)
                .clickable { onPriorityDropdownExpandedChange(true) }
                .padding(8.dp)
        ) {
            Text(
                text = when (priority) {
                    ListOfTaskStatus.HIGH -> stringResource(R.string.high)
                    ListOfTaskStatus.NORMAL -> stringResource(R.string.no)
                    ListOfTaskStatus.LOW -> stringResource(R.string.low)
                },
                color = CustomTheme.colors.labelTertiary,
                fontSize = 14.sp
            )

            DropdownMenu(
                expanded = isPriorityDropdownExpanded,
                onDismissRequest = { onPriorityDropdownExpandedChange(false) },
                modifier = Modifier.background(CustomTheme.colors.backElevated)
            ) {
                priorityItems.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            onPriorityChange(item)
                            onPriorityDropdownExpandedChange(false)
                        }
                    ) {
                        Text(
                            text = when (item) {
                                ListOfTaskStatus.HIGH -> stringResource(R.string.high)
                                ListOfTaskStatus.NORMAL -> stringResource(R.string.no)
                                ListOfTaskStatus.LOW -> stringResource(R.string.low)
                            }
                        )
                    }
                }
            }
        }

        Divider(
            color = CustomTheme.colors.supportSeparator,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .constrainAs(divider) {
                    top.linkTo(priorityText.bottom)
                }
        )

        Text(
            text = stringResource(R.string.do_before),
            modifier = Modifier.constrainAs(dateLabel) {
                start.linkTo(parent.start)
                top.linkTo(divider.bottom, margin = 16.dp)
            },
            color = CustomTheme.colors.labelPrimary,
            fontSize = 16.sp
        )

        Row(
            modifier = Modifier.constrainAs(dateSwitch) {
                end.linkTo(parent.end)
                top.linkTo(dateLabel.top)
                bottom.linkTo(dateLabel.bottom)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = isDeadlineSet,
                onCheckedChange = onDeadlineSwitchChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = BlueDark,
                    checkedTrackColor = BlueLight,
                    uncheckedThumbColor = CustomTheme.colors.backElevated,
                    uncheckedTrackColor = CustomTheme.colors.supportOverlay
                )
            )
        }

        if (deadlineComplete.isNotEmpty()) {
            Text(
                text = deadlineComplete,
                modifier = Modifier.constrainAs(dateSetText) {
                    start.linkTo(parent.start)
                    top.linkTo(dateLabel.bottom, margin = 4.dp)
                },
                color = BlueDark,
                fontSize = 14.sp
            )
        }

        Divider(
            color = CustomTheme.colors.supportSeparator,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 48.dp)
                .constrainAs(divider2) {
                    top.linkTo(dateSwitch.bottom)
                }
        )
    }
}