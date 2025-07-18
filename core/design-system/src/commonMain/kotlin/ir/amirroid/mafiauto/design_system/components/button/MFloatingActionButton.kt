package ir.amirroid.mafiauto.design_system.components.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import ir.amirroid.mafiauto.design_system.components.surface.MSurface
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.theme.locales.LocalTextStyle

@Composable
fun MFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onClickWhenDisabled: (() -> Unit)? = null,
    colors: ButtonColors = ButtonDefaults.primaryButtonColors,
    shape: Shape = ButtonDefaults.defaultShape,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    val contentColor by animateColorAsState(
        if (enabled) colors.contentColor else colors.disabledContentColor
    )
    val containerColor by animateColorAsState(
        if (enabled) colors.containerColor else colors.disabledContainerColor
    )
    MSurface(
        onClick = onClick,
        modifier = modifier
            .semantics { role = Role.Button },
        shape = shape,
        contentColor = contentColor,
        enabled = enabled,
        color = containerColor,
        onClickWhenDisabled = onClickWhenDisabled
    ) {
        CompositionLocalProvider(LocalTextStyle provides AppTheme.typography.button) {
            Row(
                modifier = Modifier.size(ButtonDefaults.fabSize),
                content = content,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
        }
    }
}