package com.msharialsayari.selectableitemslistcompose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Action<T>(
    val text: (@Composable () -> Unit),
    val icon: (@Composable () -> Unit),
    val onClicked: ((position: Int, item: T) -> Unit)? = null,
    val isVisible: Boolean,
    val actionSize: Dp = 56.dp
)