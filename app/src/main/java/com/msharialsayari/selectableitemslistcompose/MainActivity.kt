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


var DEFAULT_LIST = DataProvider.itemList



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
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }


    val deleteAction = Action<Params>(
        isVisible = true,
        action= ActionItem.Delete,
        onClicked = { items ->
            Toast.makeText(context, "Delete Action clicked and Selected items = ${items.size}", Toast.LENGTH_SHORT).show()
            viewModel.setSelectionMode(!uiState.isSelectionMode)

        })

    val pinAction = Action<Params>(
        isVisible = uiState.dataList.filter { it.isSelected }.size == 1 ,
        action= ActionItem.Pin,
        onClicked = { items ->
            Toast.makeText(context, "Pin Action clicked and Selected items = ${items.size}", Toast.LENGTH_SHORT).show()
            viewModel.setSelectionMode(!uiState.isSelectionMode)

        })




    LaunchedEffect(Unit){
        viewModel.getData()

    }

    LaunchedEffect(uiState.isSelectionMode){
        if (!uiState.isSelectionMode){
            viewModel.unselectAll()
        }

    }


    SelectableItemList(
        list = uiState.dataList,
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
            }
           viewModel.setSelectionMode(!uiState.isSelectionMode)

        } ,
        isLoading = uiState.isLoading,
        isRefreshing = isRefreshing,
        isSelectionMode = uiState.isSelectionMode,
        actions = listOf(deleteAction,pinAction),
        onRefresh={
            isRefreshing = true
            Handler(Looper.getMainLooper()).postDelayed({
                isRefreshing = false
            }, 2000)
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