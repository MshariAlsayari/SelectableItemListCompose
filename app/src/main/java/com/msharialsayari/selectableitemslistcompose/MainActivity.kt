package com.msharialsayari.selectableitemslistcompose

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.msharialsayari.selectableitemslistcompose.ui.components.Params
import com.msharialsayari.selectableitemslistcompose.ui.components.SelectableItem
import com.msharialsayari.selectableitemslistcompose.ui.components.SelectableItemList
import com.msharialsayari.selectableitemslistcompose.ui.theme.SelectableItemsListComposeTheme


var DEFAULT_LIST = DataProvider.itemList


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SelectableItemsListComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(DEFAULT_LIST)
                }
            }
        }
    }
}

@Composable
fun Greeting(list: List<Params>) {
    var listItem by remember { mutableStateOf(list) }
    var isLoading by remember { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }
    var isSelectionMode by remember { mutableStateOf(false) }

    SelectableItemList(
        list = list,
        view = { SelectableItem(params = it) },
        onItemClicked = { item, position ->

        },
        onItemLongClicked ={ item, position ->
            isSelectionMode = !isSelectionMode
        } ,
        isLoading = isLoading,
        isRefreshing = isRefreshing,
        isSelectionMode = isSelectionMode,
        onRefresh={
            isRefreshing = true
            Handler(Looper.getMainLooper()).postDelayed({
                isRefreshing = false
            }, 2000)
        },
    )











    Handler(Looper.getMainLooper()).postDelayed({
        isLoading = false
    }, 4000)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SelectableItemsListComposeTheme {
        Greeting(DEFAULT_LIST)
    }
}