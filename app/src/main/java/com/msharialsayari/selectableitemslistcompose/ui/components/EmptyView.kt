package com.msharialsayari.selectableitemslistcompose.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun EmptyViewCompose(text:String = "There is no result"){
    Text(text = text)
}