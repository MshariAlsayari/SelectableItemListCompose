package com.msharialsayari.selectableitemslistcompose.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.msharialsayari.selectableitemslistcompose.R

data class Action<T>(
    val action: ActionItem,
    val onClicked: ((item: List<T>) -> Unit)? = null,
    val isVisible: Boolean,
    val actionSize: Dp = 56.dp
)


sealed class ActionItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {


    object Pin : ActionItem(
        R.string.pin,
        R.drawable.ic_pin
    )

    object Delete : ActionItem(
        R.string.delete,
        R.drawable.ic_delete
    )


}
