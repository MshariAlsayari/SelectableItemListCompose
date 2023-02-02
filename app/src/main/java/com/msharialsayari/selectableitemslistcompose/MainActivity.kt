package com.msharialsayari.selectableitemslistcompose

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.msharialsayari.selectableitemslistcompose.ui.components.*
import com.msharialsayari.selectableitemslistcompose.ui.theme.SelectableItemsListComposeTheme
import dagger.hilt.android.AndroidEntryPoint






@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            SelectableItemsListComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel:MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val list = uiState.dataList


    val deleteAction = Action(
        isVisible = true,
        action= ActionItem.Delete,
        onClicked = { items ->
            viewModel.setSelectionMode(false)
            items.forEach {
                viewModel.removeItem(it)
            }


        })

    val pinAction = Action(
        isVisible = uiState.dataList.filter { it.isSelected }.size == 1 ,
        action= ActionItem.Pin,
        onClicked = { items ->
            viewModel.setSelectionMode(false)
            items.forEach {
                viewModel.pinItem(it)
            }

        })






    LaunchedEffect(uiState.isSelectionMode){
        if (!uiState.isSelectionMode){
            viewModel.unselectAll()
        }
    }


    SelectableItemList(
        list = list,
        view = { SelectableItem(params = it) },
        dividerView = { Divider()},
        loadingProgress = { CircularProgressIndicator()},
        emptyView = { EmptyViewCompose()},
        onItemClicked = { item, position ->
            if (uiState.isSelectionMode){
                viewModel.selectItem(item)
            }
        },
        onItemLongClicked ={ item, position ->
            if (!uiState.isSelectionMode){
                viewModel.selectItem(item)
            }else{
                viewModel.setSelectionMode(false)
            }


        } ,
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        isSelectionMode = uiState.isSelectionMode,
        actions = listOf(deleteAction,pinAction),
        onRefresh={
            viewModel.refreshList()
        },
    )








}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val viewModel: MainViewModel = hiltViewModel()
    SelectableItemsListComposeTheme {
        Greeting(viewModel)
    }
}