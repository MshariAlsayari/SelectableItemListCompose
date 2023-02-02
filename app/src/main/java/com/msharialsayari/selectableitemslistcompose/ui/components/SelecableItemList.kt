package com.msharialsayari.selectableitemslistcompose.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun  <T>  SelectableItemList(
    modifier                    : Modifier = Modifier,
    list                        : List<T>,
    view                        : @Composable (T) -> Unit,
    dividerView                 : (@Composable () -> Unit)? = null,
    emptyView                   : (@Composable () -> Unit)? = null,
    loadingProgress             : (@Composable () -> Unit)? = null,
    onItemClicked               : (item: T, position: Int) -> Unit,
    onItemLongClicked           : (item: T, position: Int) -> Unit,
    isLoading                   : Boolean = false,
    isRefreshing                : Boolean = false,
    isSelectionMode             : Boolean = false,
    actions                     : List<Action<T>> = listOf(),
    onRefresh                   : (() -> Unit)? = null,
){


    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        swipeEnabled = onRefresh != null,
        onRefresh = { onRefresh?.invoke() },
    ) {
        if (isLoading)
            LoadingView(loadingProgress)
        else{
            if (list.isNotEmpty()) {
                val selectedList = list.filter { (it as SelectableItemBase).isSelected }

                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    LazyList(
                        modifier                    = modifier,
                        list                        = list,
                        view                        = view,
                        isSelectionMode             = isSelectionMode,
                        dividerView                 = dividerView,
                        onItemClicked               = onItemClicked,
                        onItemLongClicked           = onItemLongClicked,
                    )

                    AnimatedVisibility(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        visible = isSelectionMode) {
                        ActionContainer(
                            selectedItem = selectedList,
                            actions = actions
                        )
                    }



                }

            } else {
                EmptyView(emptyView)
            }
        }
    }

}


@Composable
fun  <T>  LazyList(
    modifier                    : Modifier = Modifier,
    list                        : List<T>,
    view                        : @Composable (T) -> Unit,
    dividerView                 : (@Composable () -> Unit)? = null,
    isSelectionMode             : Boolean = false,
    onItemClicked               : (item: T, position: Int) -> Unit,
    onItemLongClicked           : (item: T, position: Int) -> Unit,
){

    LazyColumn(modifier= modifier)
     {
         itemsIndexed(list) { index, item ->
             ItemList(view,item,index,isSelectionMode,onItemClicked,onItemLongClicked )
             if (index != list.lastIndex){
                 DividerView(dividerView)
             }
         }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T>ItemList(view : @Composable (T) -> Unit,
                item:T,
                position: Int,
                isSelectionMode             : Boolean = false,
                onItemClicked               : (item: T, position: Int) -> Unit,
                onItemLongClicked           : (item: T, position: Int) -> Unit,) {

    val colorBackground = if (isSelectionMode && (item as SelectableItemBase).isSelected) Color.Blue else Color.Transparent
    val startPadding =  if (isSelectionMode) 16.dp else 0.dp
    Box(modifier =
    Modifier
        .fillMaxWidth()
        .background(if (isSelectionMode && (item as SelectableItemBase).isSelected) Color.Blue.copy(alpha = 0.1f) else Color.Transparent)
        .padding(start = startPadding)
        .combinedClickable(
            onClick = {
                onItemClicked(item, position)
            },
            onLongClick = {
                onItemLongClicked(item, position)
            },
        )) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = startPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(visible =isSelectionMode ) {
                Selection(backgroundColor = colorBackground, isSelected = (item as SelectableItemBase).isSelected)
            }

            view(item)
        }

    }
}



@Composable
fun LoadingView(view: (@Composable () -> Unit)?=null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (view != null ){
            view()
        }
    }
}

@Composable
fun EmptyView(view: (@Composable () -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (view != null ){
            view()
        }
    }
}

@Composable
fun DividerView(view: (@Composable () -> Unit)? = null) {
    if (view != null ){
        view()
    }
}