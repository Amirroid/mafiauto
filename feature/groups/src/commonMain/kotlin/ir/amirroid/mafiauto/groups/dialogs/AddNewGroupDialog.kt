package ir.amirroid.mafiauto.groups.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.common.compose.extensions.requestFocusCaching
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.dialog.MDialog
import ir.amirroid.mafiauto.design_system.components.field.MTextField
import ir.amirroid.mafiauto.design_system.components.field.TextFieldsDefaults
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddNewGroupDialog(
    onAdd: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }
    var groupName by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocusCaching()
    }

    MDialog(
        isVisible = visible,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MText(stringResource(Resources.strings.addNewGroup), style = AppTheme.typography.h2)
            MTextField(
                value = groupName,
                onValueChange = { groupName = it },
                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                placeholder = { MText(stringResource(Resources.strings.groupName)) },
                singleLine = true,
                colors = TextFieldsDefaults.outlinedTextFieldColors,
                keyboardActions = KeyboardActions(onDone = {
                    if (groupName.isEmpty()) return@KeyboardActions
                    onAdd.invoke(groupName)
                    visible = false
                }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            )
            MButton(
                onClick = {
                    onAdd.invoke(groupName)
                    visible = false
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = groupName.isNotEmpty()
            ) {
                MText(stringResource(Resources.strings.add))
            }
        }
    }
}