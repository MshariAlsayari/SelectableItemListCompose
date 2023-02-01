package com.msharialsayari.selectableitemslistcompose.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun <T> ActionItem(
    modifier: Modifier,
    action: Action<T>,
    onClicked: (position: Int, item: T) -> Unit,
    item: T,
    position: Int
) {


    AnimatedVisibility(visible = action.isVisible) {
        IconButton(
            modifier = modifier,
            onClick = {
                onClicked(position, item)
            },
            content = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    action.icon
                    action.text

                }
            }
        )
    }


}