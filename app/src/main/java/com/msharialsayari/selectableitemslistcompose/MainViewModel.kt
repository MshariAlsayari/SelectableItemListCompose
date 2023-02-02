package com.msharialsayari.selectableitemslistcompose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msharialsayari.selectableitemslistcompose.ui.components.Params
import com.msharialsayari.selectableitemslistcompose.ui.components.SelectableItemBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel()  {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    init{
        getData()
    }

    fun getData(){

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            delay(3000)
            _uiState.update {
                it.copy(dataList =  DataProvider.itemList.toMutableList() , isLoading = false,isRefreshing = false)
            }
        }


    }



    fun removeItem(item:Params){
        val newList = _uiState.value.dataList.toMutableList().apply {
            removeIf { it.id == item.id }
        }

        _uiState.update {
            it.copy(
                dataList = newList,
            )
        }
    }

    fun pinItem(item:Params){
        val newList = _uiState.value.dataList.toMutableList().apply {
            removeIf { it.id == item.id }
        }

        newList.add(0,item)

        _uiState.update {
            it.copy(
                dataList = newList,
            )
        }
    }

    fun refreshList(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isRefreshing = true)
            }
            _uiState.value.dataList.toMutableList().clear()
            delay(3000)
            _uiState.update {
                it.copy(dataList = DataProvider.itemList.toMutableList(), isLoading = false, isRefreshing = false)
            }
        }
    }

    private fun getItem(item:Params): Params? {
        return _uiState.value.dataList.find { it.id == item.id }
    }

    private fun getIndex(item:Params): Int {
        return _uiState.value.dataList.indexOf(item)
    }

    fun selectItem(item:Params){


        val index:Int
        val newList = _uiState.value.dataList.toMutableList().apply {
            index = indexOf(item)
            removeIf { it.id == item.id }
            item.apply {
                isSelected = !isSelected
            }

            add(index, item)
        }


        _uiState.update {
            it.copy(
                dataList = newList,
                isSelectionMode = _uiState.value.dataList.any { it.isSelected }
            )
        }

    }


    fun unselectAll(){

       _uiState.value.dataList.forEach {
                it.isSelected = false
        }

        val newList = _uiState.value.dataList

        _uiState.update {
            it.copy(
                dataList = newList,
                isSelectionMode = _uiState.value.dataList.any { it.isSelected }
            )
        }


    }

    fun setSelectionMode(isSelectionMode: Boolean){
        _uiState.update {
            it.copy(isSelectionMode = isSelectionMode)
        }
    }





    data class MainUiState(
        var dataList: List<Params> = mutableListOf(),
        var isLoading: Boolean = false,
        var isRefreshing: Boolean = false,
        var isSelectionMode: Boolean = false
    )
}