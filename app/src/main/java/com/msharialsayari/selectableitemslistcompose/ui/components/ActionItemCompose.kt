package com.msharialsayari.selectableitemslistcompose.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp


@Composable
fun <T> ActionItemCompose(
    modifier: Modifier = Modifier,
    action: Action<T>,
    items:List<T>,
    onClicked: (items: List<T>) -> Unit
) {


    AnimatedVisibility(visible = action.isVisible) {
        IconButton(
            modifier = modifier,
            onClick = {
                onClicked(items)
            },
            content = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Icon(painter           = painterResource(id = action.action.icon),
                        contentDescription = null,
                        tint               = Color.White
                    )
                    Text(text = stringResource(id = action.action.title), fontSize = 12.sp)


                }
            }
        )
    }


}