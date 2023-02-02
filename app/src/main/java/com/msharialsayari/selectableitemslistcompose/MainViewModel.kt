package com.msharialsayari.selectableitemslistcompose

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

    fun getData(){

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            delay(3000)
            _uiState.update {
                it.copy(dataList =  DataProvider.itemList , isLoading = false)
            }
        }


    }



    fun removeItem(item:SelectableItemBase){
        val newList = _uiState.value.dataList.toMutableList().apply {
            removeIf { it.id == item.id }
        }

        _uiState.update {
            it.copy(dataList = newList)
        }
    }

    private fun getItem(item:SelectableItemBase): SelectableItemBase? {
        return _uiState.value.dataList.find { it.id == item.id }
    }

    fun selectItem(item:Params){

//        val newList = _uiState.value.dataList.toMutableList().apply {
//            getItem(item)?.let {
//                it.isSelected = !it.isSelected
//            }
//
//        }

        _uiState.value.dataList.forEach {
            if (it.id == item.id){
                it.isSelected = !it.isSelected
            }
        }

        val newList = _uiState.value.dataList

        _uiState.update {
            it.copy(dataList = newList)
        }

    }


    fun unselectAll(){

       _uiState.value.dataList.forEach {
                it.isSelected = false
        }

        val newList = _uiState.value.dataList

        _uiState.update {
            it.copy(dataList = newList)
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