package com.msharialsayari.selectableitemslistcompose.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun <T>ActionContainer(
    modifier:Modifier = Modifier,
    selectedItem : List<T> = listOf(),
    actions: List<Action<T>> = listOf(),
) {

    val animation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animation.animateTo(1f, spring(2f))
    }


    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(85.dp)
    ) {
        Row(
            modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment     = Alignment.CenterVertically,
        ) {

            actions.forEach {
                ActionItemCompose(
                    modifier = Modifier.fillMaxHeight(),
                    action = it,
                    items = selectedItem,
                    onClicked = { items ->
                        it.onClicked?.let { it1 -> it1(items) }
                    })
            }


        }
    }
}